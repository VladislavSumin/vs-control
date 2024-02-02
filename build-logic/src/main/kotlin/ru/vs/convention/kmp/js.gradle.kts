package ru.vs.convention.kmp

/**
 * Базовая настройка js таргета для KMP.
 */

plugins {
    id("ru.vs.convention.kmp.common")
}

kotlin {
    js(IR) {
        browser()
        nodejs()
    }

    sourceSets {
        named("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
