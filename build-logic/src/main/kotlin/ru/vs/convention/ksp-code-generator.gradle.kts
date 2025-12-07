package ru.vs.convention

import ru.vladislavsumin.utils.vsCoreLibs

/**
 * Параметры по умолчанию для модулей генерации кода на основе KSP
 */

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(vsCoreLibs.vs.core.ksp.utils)
}
