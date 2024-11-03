package ru.vs.convention

/**
 * Параметры по умолчанию для модулей генерации кода на основе KSP
 */

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":core:ksp"))
}
