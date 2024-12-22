package ru.vs.rsub

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.serializer
import ru.vs.core.logger.api.logger
import ru.vs.core.logger.common.Logger
import ru.vs.rsub.RSubMessage.RSubClientMessage
import ru.vs.rsub.RSubMessage.RSubServerMessage
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * Basic implementation for any RSub client.
 * Implementation of this class generate automatically by ksp for classes annotated with [RSubClient]
 *
 * @param connector - connector impl
 * @param reconnectInterval - timeout between connection attempts
 * @param connectionKeepAliveTime - time to keep connection alive before disconnect if no active subscribers
 * @param json - [Json] instance used at internal json parsing
 * @param scope - [CoroutineScope] for launch internal asynchronous operation
 * @param logger - logger for internal logs
 */
open class RSubClientAbstract(
    private val connector: RSubConnector,

    private val reconnectInterval: Long = 3000,
    connectionKeepAliveTime: Long = 6000,

    private val json: Json = Json,
    scope: CoroutineScope = GlobalScope,
    private val logger: Logger = logger("RSubClient"),
) {
    /**
     * Contains next id, using to create new subscription with uncial id
     */
    private val nextId = atomic(0)

    /**
     * This shared flow keeps the connection open and automatically reconnects in case of errors.
     * The connection will be maintained as long as there are active subscriptions
     */
    @Suppress("TooGenericExceptionCaught")
    private val connection: Flow<ConnectionState> = channelFlow {
        logger.d { "Start observe connection" }

        // Holder for [RSubConnection]
        // In any exception, or scope cancellation we must call [RSubConnection.close] to close connection correctly
        var connectionGlobal: RSubConnection? = null

        // Global try catch block, handle uncatched exception and properly closing [connectionGlobal]
        try {

            // Send initial connection status
            send(ConnectionState.Connecting)

            while (true) {
                try {
                    // this cope prevents reconnect before old connection receive flow not closed
                    // see [crateConnectedState] function
                    coroutineScope {
                        // Establish connection
                        val connection = connector.connect()
                        connectionGlobal = connection

                        // Create connected state wrapper around connection
                        val state = crateConnectedState(connection, this)
                        send(state)
                    }
                } catch (e: Exception) {
                    when (e) {
                        // Handle known RSubExpectedExceptionOnConnectionException
                        // These exceptions not dramatically failed all scope, but triggers reconnect
                        is RSubExpectedExceptionOnConnectionException -> {
                            logger.d { "Connection or listening failed with checked exception: ${e.message}" }
                            send(ConnectionState.ConnectionFailed(e))
                            connectionGlobal?.close()
                            delay(reconnectInterval)
                            logger.d { "Reconnecting..." }
                        }

                        is CancellationException -> throw e

                        // For other unknown exceptions wrap it and throw outside connection scope
                        else -> {
                            val message = "Unknown exception on connection or listening"
                            val exception = RSubException(message, e)
                            logger.e(exception) { message }
                            throw exception
                        }
                    }
                }
            }
        } finally {
            logger.d { "Stopping observe connection" }
            withContext(NonCancellable) {
                connectionGlobal?.close()
                logger.d { "Stop observe connection" }
            }
        }
    }
        .distinctUntilChanged()
        .onEach { logger.d { "New connection status: ${it.status}" } }
        .shareIn(
            scope + CoroutineName("RSubClient::connection"),
            SharingStarted.WhileSubscribed(
                stopTimeoutMillis = connectionKeepAliveTime,
                replayExpirationMillis = 0,
            ),
            1,
        )

    /**
     * Keep connection active while subscribed, return actual connection status
     */
    fun observeConnectionStatus(): Flow<RSubConnectionStatus> = connection.map { it.status }

    protected suspend inline fun <reified T : Any> processSuspend(
        interfaceName: String,
        methodName: String,
        argumentsTypes: List<KType>,
        arguments: List<Any>,
    ): T = processSuspend(interfaceName, methodName, typeOf<T>(), argumentsTypes, arguments)

    @Suppress("TooGenericExceptionCaught")
    protected suspend fun <T : Any> processSuspend(
        interfaceName: String,
        methodName: String,
        type: KType,
        argumentsTypes: List<KType>,
        arguments: List<Any?>,
    ): T {
        return withConnection { connection ->
            val id = nextId.getAndIncrement()
            try {
                coroutineScope {
                    val responseDeferred = async { connection.incoming.filter { it.id == id }.first() }
                    // yield для гарантии старта async ДО подписки
                    yield()
                    connection.subscribe(id, interfaceName, methodName, argumentsTypes, arguments)
                    val response = responseDeferred.await()
                    parseServerMessage<T>(response, type)
                }
            } catch (e: Exception) {
                when (e) {
                    is CancellationException,
                    is RSubServerException,
                    -> throw e

                    else -> throw RSubException("Unknown exception", e)
                }
            }
        }
    }

    protected inline fun <reified T : Any> processFlow(
        interfaceName: String,
        methodName: String,
        argumentsTypes: List<KType>,
        arguments: List<Any?>,
    ): Flow<T> = processFlow(interfaceName, methodName, typeOf<T>(), argumentsTypes, arguments)

    @Suppress("TooGenericExceptionCaught")
    protected fun <T : Any> processFlow(
        interfaceName: String,
        methodName: String,
        type: KType,
        argumentsTypes: List<KType>,
        arguments: List<Any?>,
    ): Flow<T> = channelFlow {
        // Check reconnect policy
        val throwException = true
//        val throwException = when (
//            methodName.findAnnotation<RSubFlowPolicy>()?.policy ?: RSubFlowPolicy.Policy.THROW_EXCEPTION
//        ) {
//            RSubFlowPolicy.Policy.THROW_EXCEPTION -> true
//            RSubFlowPolicy.Policy.SUPPRESS_EXCEPTION_AND_RECONNECT -> false
//        }

        withConnection(throwException) { connection ->
            val id = nextId.getAndIncrement()
            try {
                coroutineScope {
                    launch {
                        connection.incoming
                            .filter { it.id == id }
                            .collect {
                                val item = parseServerMessage<T>(it, type)
                                send(item)
                            }
                    }
                    // yield для гарантии старта launch ДО подписки
                    yield()
                    connection.subscribe(id, interfaceName, methodName, argumentsTypes, arguments)
                }
            } catch (e: Exception) {
                when (e) {
                    is FlowCompleted -> {
                        // suppress
                    }

                    else -> {
                        withContext(NonCancellable) {
                            connection.unsubscribe(id)
                        }
                        throw e
                    }
                }
            }
        }
    }

    /**
     * Create wrapped connection, with shared receive flow
     * all received messages reply to that flow, flow haven`t buffer!
     *
     * @param connection raw connection from connector
     * @param scope coroutine scope of current connection session
     */
    private fun crateConnectedState(connection: RSubConnection, scope: CoroutineScope): ConnectionState.Connected {
        return ConnectionState.Connected(
            send = { connection.send(json.encodeToString(it)) },
            incoming = connection.receive
                .map { json.decodeFromString<RSubServerMessage>(it) }
                .onEach { logger.t { "Received message: $it" } }
                // Hot observable, subscribe immediately, shared, no buffer, connection scoped
                .shareIn(scope, SharingStarted.Eagerly),
        )
    }

    /**
     * Try to subscribe to [connection], wait connected state and execute given block with [ConnectionState.Connected]
     * If connection failed throw exception
     *
     * @param throwOnDisconnect - if false then suppress network exception form socket and [block],
     * and then if false retry to call [block]
     */
    @Suppress("TooGenericExceptionThrown")
    private suspend fun <T> withConnection(
        throwOnDisconnect: Boolean = true,
        block: suspend (connection: ConnectionState.Connected) -> T,
    ): T {
        return connection
            // Filtering initial connecting state and check connection failed state
            .filter { connectionState ->
                when (connectionState) {
                    is ConnectionState.Connecting -> false
                    is ConnectionState.Connected -> true
                    is ConnectionState.ConnectionFailed ->
                        // Check if we need throw exception on connection error
                        if (throwOnDisconnect) {
                            throw RSubException("Connection in state DISCONNECTED")
                        } else {
                            false
                        }
                }
            }
            // Filter can be passing only connection state, we don't need using filterInstance
            .map { it as ConnectionState.Connected }
            // Hack, use mapLatest to prevent closing connection.
            // Connection subscription active all time while block executing
            .mapLatest(block)
            .retry {
                when {
                    // TODO unexpected coroutine behavior, check if fixed on new version
//                    it is CancellationException -> {
//                        log.warn("Connection was canceled by previous connection")
//                        true
//                    }
                    throwOnDisconnect -> false
//                    it is SocketException -> true
                    else -> {
                        logger.e(it) { "Unexpected exception" }
                        false
                    }
                }
            }
            .first()
    }

    @Suppress("ThrowsCount")
    private fun <T : Any> parseServerMessage(message: RSubServerMessage, type: KType): T = when (message) {
        is RSubServerMessage.Data -> {
            json.decodeFromJsonElement(json.serializersModule.serializer(type), message.data) as T
        }

        is RSubServerMessage.FlowComplete -> throw FlowCompleted()
        is RSubServerMessage.Error -> throw RSubServerException("Server return error")
    }

    private suspend fun ConnectionState.Connected.subscribe(
        id: Int,
        name: String,
        methodName: String,
        argumentsTypes: List<KType>,
        arguments: List<Any?>,
    ) {
        val serializedArguments = arguments
            .zip(argumentsTypes)
            .map { (value, type) -> json.encodeToJsonElement(json.serializersModule.serializer(type), value) }
        send(RSubClientMessage.Subscribe(id, name, methodName, serializedArguments))
    }

    private suspend fun ConnectionState.Connected.unsubscribe(id: Int) = send(RSubClientMessage.Unsubscribe(id))

    /**
     * Represents states of server connection states
     */
    private sealed class ConnectionState(val status: RSubConnectionStatus) {
        object Connecting : ConnectionState(RSubConnectionStatus.Connecting)
        class Connected(
            val send: suspend (message: RSubMessage) -> Unit,
            val incoming: Flow<RSubServerMessage>,
        ) : ConnectionState(RSubConnectionStatus.Connected)

        class ConnectionFailed(error: Exception) : ConnectionState(RSubConnectionStatus.Reconnecting(error))
    }

    /**
     * Special exception throws when server returns [RSubServerMessage.FlowComplete]
     * Later this expedition catch and correctly close client side flow
     */
    private class FlowCompleted : Exception()
}
