package ru.vs.rsub

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer
import ru.vladislavsumin.core.logger.api.logger
import ru.vladislavsumin.core.logger.common.Logger
import ru.vs.rsub.RSubMessage.RSubClientMessage
import ru.vs.rsub.RSubMessage.RSubServerMessage
import kotlin.reflect.KType

/**
 * Entry point for rSub server code.
 *
 * @param rSubProxies - множество прокси файлов, генерируемых через [RSubServerInterfaces]
 * @param protobuf - pass here custom protobuf serializer with included your polymorphic class serializers
 * @param logger - logger for internal rSub logging
 */
class RSubServer(
    rSubProxies: Set<RSubServerInterface>,
    private val protobuf: ProtoBuf = ProtoBuf,
    private val logger: Logger = logger("RSubServer"),
) {

    // TODO добавить защиту от дублей
    private val proxyMap = rSubProxies.associateBy { it.rSubName }

    /**
     * Process new [RSubConnection], this function block calling coroutine until connection open.
     * Cancelling this function will close the connection
     */
    suspend fun handleNewConnection(connection: RSubConnection): Unit = coroutineScope {
        ConnectionHandler(connection).handle()
    }

    private inner class ConnectionHandler(
        private val connection: RSubConnection,
    ) {
        /**
         * Map of active subscriptions
         * key - unique subscription id
         * value - coroutine job associated with given subscription id
         */
        private val activeSubscriptions = mutableMapOf<Int, Job>()

        suspend fun handle() {
            coroutineScope {
                logger.d { "Handle new connection" }
                connection.receive.collect { rawRequest ->
                    when (val request = protobuf.decodeFromByteArray<RSubClientMessage>(rawRequest)) {
                        is RSubClientMessage.Subscribe -> processSubscribe(request, this)
                        is RSubClientMessage.Unsubscribe -> processUnsubscribe(request)
                    }
                }
            }
            activeSubscriptions.forEach { (_, v) -> v.cancel() }
            connection.close()
            logger.d { "Connection closed" }
        }

        /**
         * Encodes [message] to Json and send it to client
         */
        private suspend fun send(message: RSubMessage) {
            connection.send(protobuf.encodeToByteArray(message))
        }

        // TODO check possible data corrupt on id error from client (add sync?)
        // TODO add error handling
        // TODO make cancelable
        @Suppress("TooGenericExceptionCaught", "InstanceOfCheckForException")
        private suspend fun processSubscribe(request: RSubClientMessage.Subscribe, scope: CoroutineScope) {
            val job = scope.launch(start = CoroutineStart.LAZY) {
                logger.t { "Subscribe id=${request.id} to ${request.interfaceName}::${request.functionName}" }

                val impl = proxyMap[request.interfaceName]!!.rSubSubscriptions[request.functionName]!!

                try {
                    when (impl) {
                        is RSubServerSubscription.SuspendSub<*> -> {
                            val response = impl.get(parseArguments(impl, request.arguments))
                            sendData(request.id, response, impl.type)
                        }

                        is RSubServerSubscription.FlowSub<*> -> {
                            val flow = impl.get(parseArguments(impl, request.arguments))
                            flow.collect { sendData(request.id, it, impl.type) }
                            send(RSubServerMessage.FlowComplete(request.id))
                        }
                    }
                } catch (e: Exception) {
                    send(RSubServerMessage.Error(request.id))
                    activeSubscriptions.remove(request.id)

                    if (e is CancellationException) throw e
                    logger.w(e) {
                        "Error on subscription id=${request.id} to ${request.interfaceName}::${request.functionName}"
                    }
                    return@launch
                }

                logger.t {
                    "Complete subscription id=${request.id} to ${request.interfaceName}::${request.functionName}"
                }
                activeSubscriptions.remove(request.id)
            }
            activeSubscriptions[request.id] = job
            job.start()
        }

        private fun parseArguments(
            impl: RSubServerSubscription,
            arguments: List<ByteArray?>,
        ): List<Any?> {
            check(impl.argumentTypes.size == arguments.size)
            return impl.argumentTypes.zip(arguments) { type, instance ->
                if (instance != null) {
                    protobuf.decodeFromByteArray(protobuf.serializersModule.serializer(type), instance)
                } else {
                    null
                }
            }
        }

        private fun processUnsubscribe(request: RSubClientMessage.Unsubscribe) {
            logger.t { "Cancel subscription id=${request.id}" }
            activeSubscriptions.remove(request.id)?.cancel()
        }

        private suspend fun sendData(id: Int, data: Any?, type: KType) {
            val responsePayload = protobuf.encodeToByteArray(protobuf.serializersModule.serializer(type), data)
            val message = RSubServerMessage.Data(id, responsePayload)
            send(message)
        }
    }
}
