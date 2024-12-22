package ru.vs.rsub

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Set of messages to communicate between client and server.
 */
@Serializable
sealed interface RSubMessage {
    val id: RSubSubscriptionId

    /**
     * Set of messages witch client can send to server
     */
    @Serializable
    sealed interface RSubClientMessage : RSubMessage {
        /**
         * Subscribes to new subscription it may be flow return function or suspend function
         * @param interfaceName - interface name to find in server implementations
         * @param functionName - function inside [interfaceName]
         * @param arguments - function arguments
         */
        @Serializable
        @SerialName("subscribe")
        data class Subscribe(
            override val id: Int,
            val interfaceName: String,
            val functionName: String,
            val arguments: List<JsonElement?>,
        ) : RSubClientMessage

        /**
         * Unsubscribe to previously subscribed subscription id
         */
        @Serializable
        @SerialName("unsubscribe")
        data class Unsubscribe(
            override val id: RSubSubscriptionId,
        ) : RSubClientMessage
    }

    /**
     * Set of messages witch server can send to client
     */
    @Serializable
    sealed interface RSubServerMessage : RSubMessage {
        /**
         * Response with requested data.
         * For Flow subscription it's one element emitted by flow
         * For suspend subscription it's final result and end of subscription
         */
        @Serializable
        @SerialName("data")
        data class Data(
            override val id: RSubSubscriptionId,
            val data: JsonElement,
        ) : RSubServerMessage

        /**
         * Sends when flow completing, this means end of Flow subscription
         */
        @Serializable
        @SerialName("flow_complete")
        data class FlowComplete(
            override val id: RSubSubscriptionId,
        ) : RSubServerMessage

        /**
         * Sends when server interface implementation throw error or flow ends with error
         */
        @Serializable
        @SerialName("error")
        data class Error(
            override val id: RSubSubscriptionId,
        ) : RSubServerMessage
    }
}
