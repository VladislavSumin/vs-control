import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    id("ru.vs.convention.kmp.wasm")
    id("ru.vs.convention.compose")
}

compose.experimental.web.application {}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "main.js"
            }
        }
    }

    sourceSets {
        wasmJsMain.dependencies {
            implementation(projects.client.common)
        }
    }
}
