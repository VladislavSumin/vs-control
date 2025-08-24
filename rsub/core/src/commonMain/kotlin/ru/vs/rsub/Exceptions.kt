@file:Suppress("MatchingDeclarationName", "Filename")

package ru.vs.rsub

/**
 * Basic rSub exception
 */
open class RSubException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

/**
 * Выбрасывается из потока когда соединение разрывается при открытом потоке.
 */
class RSubFlowDisconnectedException : RuntimeException("Connection in state DISCONNECTED")

/**
 * throw this when catch expected exception while connection
 * if that exception catch, then rSubClient try to reconnect
 */
class RSubExpectedExceptionOnConnectionException(message: String, cause: Throwable) : RSubException(message, cause)
