package ru.vs.rsub

/**
 * Содержит описание методов интерфейса для использования их в [RSubServer].
 */
interface RSubServerInterface {
    val rSubName: String
    val rSubSubscriptions: Map<String, RSubServerSubscription>
}
