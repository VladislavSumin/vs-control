package ru.vs.convention.kmp

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

/**
 * Базовая настройка unit тестов для kmp.
 */

plugins {
    id("kotlin-multiplatform")
}

val libs = the<LibrariesForLibs>()

kotlin {
    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.kotlin.coroutines.test)
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
