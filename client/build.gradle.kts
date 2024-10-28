import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("ru.vs.convention.kmp.all-non-android")
    id("ru.vs.convention.kmp.android-application")
    id("ru.vs.convention.compose")
}

compose.experimental.web.application {}

android {
    namespace = "ru.vs.control"

    defaultConfig {
        applicationId = "ru.vs.control"
    }
}

kotlin {
    js {
        binaries.executable()

        browser {
            commonWebpackConfig {
                outputFileName = "main.js"
            }
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "main.js"
            }
        }
    }

    macosX64 {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    macosArm64 {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.logger.api)
            implementation(projects.core.logger.platform)

            implementation(projects.core.compose)
            implementation(projects.core.decompose)
            implementation(projects.core.di)
            implementation(projects.core.navigation.impl)

            // Features
            implementation(projects.feature.appInfo.clientImpl)
            implementation(projects.feature.debugScreen.clientImpl)
            implementation(projects.feature.initialization.clientImpl)
            implementation(projects.feature.initializedRootScreen.clientImpl)
            implementation(projects.feature.mainScreen.clientImpl)
            implementation(projects.feature.navigationRootScreen.clientImpl)
            implementation(projects.feature.rootContentScreen.clientImpl)
            implementation(projects.feature.rootScreen.clientImpl)
            implementation(projects.feature.servers.clientImpl)
            implementation(projects.feature.splashScreen.clientImpl)
            implementation(projects.feature.welcomeScreen.clientImpl)
        }
    }
}
