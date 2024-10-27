package ru.vs.convention.kmp

import org.gradle.accessors.dm.LibrariesForLibs

/**
 * Базовая настройка KMP.
 */

plugins {
    id("kotlin-multiplatform")
}

val libs = the<LibrariesForLibs>()
kotlin {
    // Включаем автоматическую генерацию source set`ов. Подробнее читайте документацию по функции.
    applyDefaultHierarchyTemplate()

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
