package ru.vs.rsub.connector.ktor_websocket

/**
 * Check if given exception is expected for current platform
 */
internal expect fun Exception.isPlatformExpectedException(): Boolean
