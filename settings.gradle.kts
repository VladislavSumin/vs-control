enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

apply { from("build-logic/common-settings.gradle.kts") }

pluginManagement {
    includeBuild("build-logic")
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

include(":custom-detekt-rules")

include(":core:collections:tree")
include(":core:compose")
include(":core:compose-test")
include(":core:coroutines")
include(":core:coroutines-test")
include(":core:decompose")
include(":core:decompose-test")
include(":core:di")
include(":core:factory-generator:api")
include(":core:factory-generator:ksp")
include(":core:ksp")
include(":core:logger:api")
include(":core:logger:common")
include(":core:logger:internal")
include(":core:logger:manager")
include(":core:logger:platform")
include(":core:navigation:api")
include(":core:navigation:impl")
include(":core:navigation:factory-generator:api")
include(":core:navigation:factory-generator:ksp")
include(":core:properties:api")
include(":core:serialization:json")
include(":core:shared-element-transition")
include(":core:splash")
include(":core:uikit:graph")
include(":core:uikit:icons")
include(":core:uikit:paddings")
include(":core:utils")

feature("app-info")
feature("debug-screen")
feature("initialization")
feature("initialized-root-screen")
feature("main-screen")
feature("navigation-root-screen")
feature("root-content-screen")
feature("root-screen")
feature("servers")
feature("splash-screen")
feature("welcome-screen")

include("client")

include(":server:jvm")

/**
 * Подключает иерархию фичи, согласно стандартному набору модулей.
 * @param name имя фичи.
 */
fun feature(name: String) {
    include(":feature:$name:client-api")
    include(":feature:$name:client-impl")
}
