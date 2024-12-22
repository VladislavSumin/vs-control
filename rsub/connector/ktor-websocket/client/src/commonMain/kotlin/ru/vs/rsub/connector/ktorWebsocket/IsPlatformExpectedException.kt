package ru.vs.rsub.connector.ktorWebsocket

/**
 * Check if given exception is expected for current platform
 */
internal expect fun Exception.isPlatformExpectedException(): Boolean
