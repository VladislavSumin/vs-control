package ru.vs.rsub

/**
 * Creates [RSubConnection]
 */
interface RSubConnector {
    /**
     * Try to establish connection.
     *
     * On failed to establish connection throw [RSubExpectedExceptionOnConnectionException] if failed by network
     * problem. This signal for rSub try to apply reconnection policy. If throw any other exception rSub wrap and
     * rethrow it up.
     *
     * @return connected instance if [RSubConnection].
     * rSub guarantee call [RSubConnection.close] after finish use connection
     */
    suspend fun connect(): RSubConnection
}
