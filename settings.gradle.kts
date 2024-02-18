enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")

    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

plugins {
    // Плагин для публикации gradle scans
    id("com.gradle.enterprise") version "3.16.2"
}

rootProject.name = "vs-control"

val isCI = extra["ru.vs.core.ci"].toString().toBoolean()

// Включаем публикацию gradle scans для всех билдов на CI
gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(isCI)
    }
}

include(":core:compose")
include(":core:coroutines")
include(":core:decompose")
include(":core:di")
include(":core:logger:api")
include(":core:logger:common")
include(":core:logger:internal")
include(":core:logger:manager")
include(":core:logger:platform")

feature("app-info")
feature("initialization")
feature("initialized-root-screen")
feature("root-screen")
feature("splash-screen")

include("client:common")
include("client:android")
include("client:js")
include("client:jvm")
include("client:macos")
include("client:wasm")

include(":server:jvm")

/**
 * Подключает иерархию фичи, согласно стандартному набору модулей.
 * @param name имя фичи.
 */
fun feature(name: String) {
    include(":feature:$name:client-api")
    include(":feature:$name:client-impl")
}
