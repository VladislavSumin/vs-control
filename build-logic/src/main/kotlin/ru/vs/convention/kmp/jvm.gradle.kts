package ru.vs.convention.kmp

/**
 * Базовая настройка JVM таргета для KMP.
 */

plugins {
    id("ru.vs.convention.kmp.common")
}

kotlin {
    jvm()

    sourceSets {
        named("jvmTest") {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
    }
}
