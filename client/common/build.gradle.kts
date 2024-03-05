import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

compose.experimental.web.application {}

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
            api(projects.core.logger.api)
            implementation(projects.core.logger.platform)

            api(projects.core.compose)
            api(projects.core.decompose)
            api(projects.core.di)
            api(projects.core.navigation.impl)

            // Features
            api(projects.feature.appInfo.clientImpl)
            api(projects.feature.debugScreen.clientImpl)
            api(projects.feature.initialization.clientImpl)
            api(projects.feature.initializedRootScreen.clientImpl)
            api(projects.feature.navigationRootScreen.clientImpl)
            api(projects.feature.rootContentScreen.clientImpl)
            api(projects.feature.rootScreen.clientImpl)
            api(projects.feature.splashScreen.clientImpl)
            api(projects.feature.welcomeScreen.clientImpl)
        }
    }
}
