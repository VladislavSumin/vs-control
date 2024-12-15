package ru.vs.convention.kmp

/**
 * Базовая настройка js таргета для KMP.
 */

plugins {
    id("ru.vladislavsumin.convention.kmp.common")
}

kotlin {
    js {
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
