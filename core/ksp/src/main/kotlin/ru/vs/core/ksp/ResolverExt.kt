package ru.vs.core.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated

inline fun <reified T : Annotation> Resolver.processAnnotated(noinline block: (KSAnnotated) -> Unit): List<KSAnnotated> =
    processAnnotated(annotationName = T::class.qualifiedName!!, block)

// TODO сделать inline
fun Resolver.processAnnotated(annotationName: String, block: (KSAnnotated) -> Unit): List<KSAnnotated> {

    // TODO провести эксперименты по упрощению кода этой функции
    fun processAnnotated(annotated: KSAnnotated): Boolean {
        return try {
            block(annotated)
            true
        } catch (_: IllegalArgumentException) {
            // We have cases when one generated factory using inside another generated factory,
            // for these cases we need to processing sources with more than once iteration
            false
        } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
            // logger.exception(e)
            false
        }
    }

    return getSymbolsWithAnnotation(annotationName)
        .filterNot(::processAnnotated)
        .toList()
}