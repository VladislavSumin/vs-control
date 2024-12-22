package ru.vs.rsub

sealed interface RSubConnectionStatus {
    /**
     * Initial connection status, this means it's first connection attempt
     */
    object Connecting : RSubConnectionStatus

    /**
     * Successfully connected
     */
    object Connected : RSubConnectionStatus

    /**
     * Reconnecting (or waits reconnect timeout) after connection failed
     * @param connectionError - last connection try error
     */
    data class Reconnecting(val connectionError: Exception) : RSubConnectionStatus

    /**
     * TODO NOW UNUSED
     * TODO добавить пасивного наблюдателя за состоянием соединения
     * Disconnected (and no connection attempts, because have no subscribers
     */
    // object Disconnected : RSubConnectionStatus
}
