package ru.vs.rsub.connector.ktor_websocket

import java.nio.channels.UnresolvedAddressException

internal actual fun Exception.isPlatformExpectedException(): Boolean = when (this) {
    is UnresolvedAddressException -> true
    else -> false
}
