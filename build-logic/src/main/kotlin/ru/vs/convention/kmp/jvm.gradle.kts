package ru.vs.convention.kmp

import org.gradle.accessors.dm.LibrariesForLibs

/**
 * Базовая настройка JVM таргета для KMP.
 */

plugins {
    id("ru.vs.convention.kmp.common")
}

val libs = the<LibrariesForLibs>()

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
