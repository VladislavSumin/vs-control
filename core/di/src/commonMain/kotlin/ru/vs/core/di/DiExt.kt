package ru.vs.core.di

import org.kodein.di.DirectDIAware
import org.kodein.di.instance

/**
 * Короткий alias для instance().
 */
inline fun <reified T : Any> DirectDIAware.i(tag: Any? = null): T = instance(tag)
