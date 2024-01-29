package ru.vs.utils

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * Предоставляет доступ к [BaseExtension] вне зависимости от типа подключенного андроид плагина.
 */
val Project.android: BaseExtension
    get() = extensions.getByType()

/**
 * Предоставляет доступ к [BaseExtension] вне зависимости от типа подключенного андроид плагина.
 */
fun Project.android(block: BaseExtension.() -> Unit) = android.block()

/**
 * Предоставляет доступ к [KotlinJvmOptions] вне зависимости от типа подключенного андроид плагина.
 */
val BaseExtension.kotlinOptions: KotlinJvmOptions
    get() = (this as ExtensionAware).extensions.getByType()

/**
 * Предоставляет доступ к [KotlinJvmOptions] вне зависимости от типа подключенного андроид плагина.
 */
fun BaseExtension.kotlinOptions(block: KotlinJvmOptions.() -> Unit) = kotlinOptions.block()
