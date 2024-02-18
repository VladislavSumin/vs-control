package ru.vs.convention

import org.gradle.accessors.dm.LibrariesForLibs

/**
 * Настройки по умолчанию для atomicfu плагина.
 */

plugins {
    id("kotlin-multiplatform")
    id("kotlinx-atomicfu")
}

val libs = the<LibrariesForLibs>()
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.atomicfu)
        }
    }
}
