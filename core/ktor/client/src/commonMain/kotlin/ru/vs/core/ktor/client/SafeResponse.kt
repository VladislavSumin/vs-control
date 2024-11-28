package ru.vs.core.ktor.client

/**
 * TODO доку
 */
@Suppress("TooGenericExceptionCaught")
suspend inline fun <T> handleConnectionErrors(
    block: suspend () -> T,
): SafeResponse<T> {
    return try {
        SafeResponse.Success(block())
    } catch (e: Exception) {
        when {
            else -> SafeResponse.UnknownError(e)
        }
    }
}

sealed interface SafeResponse<T> {
    data class Success<T>(val response: T) : SafeResponse<T>

    sealed interface Error<T> : SafeResponse<T> {
        val error: Exception
    }

    data class UnknownError<T>(override val error: Exception) : Error<T>
}
