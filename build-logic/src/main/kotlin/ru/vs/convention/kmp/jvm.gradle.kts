package ru.vs.convention.kmp

import ru.vs.utils.libs

/**
 * Базовая настройка JVM таргета для KMP.
 */

plugins {
    id("ru.vladislavsumin.convention.kmp.common")
}

kotlin {
    jvm()

    sourceSets {
        jvmTest {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation(libs.mockk)
            }
        }
    }
}
