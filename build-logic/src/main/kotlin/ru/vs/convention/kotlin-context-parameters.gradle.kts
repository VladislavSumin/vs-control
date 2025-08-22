package ru.vs.convention

/**
 * Включает kotlin context parameters.
 */

plugins {
    id("kotlin-multiplatform")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}
