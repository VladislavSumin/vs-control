package ru.vs.rsub

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer
import ru.vs.rsub.RSubMessage.RSubClientMessage
import ru.vs.rsub.RSubMessage.RSubServerMessage
import kotlin.reflect.KType

/**
 * Entry point for rSub server code.
 *
 * Implementation of [RSubServerSubscriptionsAbstract] may generate automatically by KSP
 * @see [RSubServerSubscriptions] annotation
 *
 * @param rSubServerSubscriptions - set of your subscriptions implementations
 * @param json - pass here custom json serializer with included your polymorphic class serializers
 * @param logger - logger for internal rSub logging
 */
class RSubServer(
    private val rSubServerSubscriptions: RSubServerSubscriptionsAbstract,
    private val json: Json = Json,
    private val logger: KLogger = KotlinLogging.logger("RSubServer"),
) {

    /**
     * Process new [RSubConnection], this function block calling coroutine until connection open.
     * Cancelling this function will close the connection
     */
    suspend fun handleNewConnection(connection: RSubConnection): Unit = coroutineScope {
        ConnectionHandler(connection).handle()
    }

    private inner class ConnectionHandler(
        private val connection: RSubConnection
    ) {
        /**
         * Map of active subscriptions
         * key - unique subscription id
         * value - coroutine job associated with given subscription id
         */
        private val activeSubscriptions = mutableMapOf<Int, Job>()

        suspend fun handle() {
            coroutineScope {
                logger.debug { "Handle new connection" }
                connection.receive.collect { rawRequest ->
                    when (val request = Json.decodeFromString<RSubClientMessage>(rawRequest)) {
                        is RSubClientMessage.Subscribe -> processSubscribe(request, this)
                        is RSubClientMessage.Unsubscribe -> processUnsubscribe(request)
                    }
                }
            }
            activeSubscriptions.forEach { (_, v) -> v.cancel() }
            connection.close()
            logger.debug { "Connection closed" }
        }

        /**
         * Encodes [message] to Json and send it to client
         */
        private suspend fun send(message: RSubMessage) {
            connection.send(json.encodeToString(message))
        }

        // TODO check possible data corrupt on id error from client (add sync?)
        // TODO add error handling
        // TODO make cancelable
        @Suppress("TooGenericExceptionCaught", "InstanceOfCheckForException")
        private suspend fun processSubscribe(request: RSubClientMessage.Subscribe, scope: CoroutineScope) {
            val job = scope.launch(start = CoroutineStart.LAZY) {
                logger.trace { "Subscribe id=${request.id} to ${request.interfaceName}::${request.functionName}" }

                val impl = rSubServerSubscriptions.getImpl(request.interfaceName, request.functionName)

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
                    logger.warn(e) {
                        "Error on subscription id=${request.id} to ${request.interfaceName}::${request.functionName}"
                    }
                    return@launch
                }

                logger.trace { "Complete subscription id=${request.id} to ${request.interfaceName}::${request.functionName}" }
                activeSubscriptions.remove(request.id)
            }
            activeSubscriptions[request.id] = job
            job.start()
        }

        private fun parseArguments(
            impl: RSubServerSubscription,
            arguments: List<JsonElement?>
        ): List<Any?> {
            check(impl.argumentTypes.size == arguments.size)
            return impl.argumentTypes.zip(arguments) { type, instance ->
                if (instance != null)
                    json.decodeFromJsonElement(json.serializersModule.serializer(type), instance)
                else null
            }
        }

        private fun processUnsubscribe(request: RSubClientMessage.Unsubscribe) {
            logger.trace { "Cancel subscription id=${request.id}" }
            activeSubscriptions.remove(request.id)?.cancel()
        }

        private suspend fun sendData(id: Int, data: Any?, type: KType) {
            val responsePayload = json.encodeToJsonElement(json.serializersModule.serializer(type), data)
            val message = RSubServerMessage.Data(id, responsePayload)
            send(message)
        }
    }
}
