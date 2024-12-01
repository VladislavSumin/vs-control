import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("ru.vs.convention.kmp.all-non-android")
    id("ru.vs.convention.kmp.android-application")
    id("ru.vs.convention.compose")
    id("app.cash.sqldelight")
}

// TODO вынести в конвеншен
// TODO сделать dsl
evaluationDependsOn(":feature:embedded-server:client-impl")
sqldelight {
    databases {
        register("Database") {
            packageName.set("ru.vs.control.repository")
            dependency(projects.feature.embeddedServer.clientImpl)
        }
    }
}

compose.experimental.web.application {}

android {
    namespace = "ru.vs.control"

    defaultConfig {
        applicationId = "ru.vs.control"
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")

            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
            )
        }
    }

    packaging {
        resources.excludes.add("META-INF/INDEX.LIST")
        resources.excludes.add("META-INF/DEPENDENCIES")
        // TODO удалить после перехода на CIO
        resources.pickFirsts.add("META-INF/io.netty.versions.properties")
    }
}

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass.set("ru.vs.control.MainKt")
        }
    }

    js {
        binaries.executable()

        browser {
            commonWebpackConfig {
                outputFileName = "main.js"
            }
        }
    }

// TODO Включить обратно
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        binaries.executable()
//        browser {
//            commonWebpackConfig {
//                outputFileName = "main.js"
//            }
//        }
//    }

// TODO Включить обратно
//    macosX64 {
//        binaries {
//            executable {
//                entryPoint = "main"
//            }
//        }
//    }
//
//    macosArm64 {
//        binaries {
//            executable {
//                entryPoint = "main"
//            }
//        }
//    }

    sourceSets {
        commonMain.dependencies {
            // TODO вынести в конвеншен
            implementation(projects.core.database)

            implementation(projects.core.logger.api)
            implementation(projects.core.logger.platform)

            implementation(projects.core.autoload)
            implementation(projects.core.coroutines)
            implementation(projects.core.compose)
            implementation(projects.core.decompose)
            implementation(projects.core.di)
            implementation(projects.core.ktor.client)
            implementation(projects.core.fs.impl)
            implementation(projects.core.navigation.impl)
            implementation(projects.core.properties.impl)

            // Features
            implementation(projects.feature.appInfo.clientImpl)
            implementation(projects.feature.auth.clientImpl)
            implementation(projects.feature.debugScreen.clientImpl)
            implementation(projects.feature.embeddedServer.clientImpl)
            implementation(projects.feature.initialization.clientImpl)
            implementation(projects.feature.initializedRootScreen.clientImpl)
            implementation(projects.feature.mainScreen.clientImpl)
            implementation(projects.feature.navigationRootScreen.clientImpl)
            implementation(projects.feature.rootContentScreen.clientImpl)
            implementation(projects.feature.rootScreen.clientImpl)
            implementation(projects.feature.serverInfo.clientImpl)
            implementation(projects.feature.servers.clientImpl)
            implementation(projects.feature.settingsScreen.clientImpl)
            implementation(projects.feature.splashScreen.clientImpl)
            implementation(projects.feature.welcomeScreen.clientImpl)
        }
    }
}
