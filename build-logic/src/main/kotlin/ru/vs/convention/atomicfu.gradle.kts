package ru.vs.convention

import ru.vs.utils.libs

/**
 * Настройки по умолчанию для atomicfu плагина.
 */

plugins {
    id("kotlin-multiplatform")
    id("org.jetbrains.kotlinx.atomicfu")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.atomicfu)
        }
    }
}
