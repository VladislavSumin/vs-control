package ru.vs.convention.kmp

import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

/**
 * Базовая настройка wasm таргета для KMP.
 */

plugins {
    id("ru.vs.convention.kmp.common")
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        named("wasmJsTest").dependencies {
            implementation(kotlin("test-wasm-js"))
        }
    }
}
