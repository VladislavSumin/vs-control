package ru.vs.convention

import ru.vs.utils.libs

/**
 * Параметры по умолчанию для модулей генерации кода на основе KSP
 */

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.kotlin.ksp)
    implementation(libs.kotlinpoet.core)
    implementation(libs.kotlinpoet.ksp)
}
