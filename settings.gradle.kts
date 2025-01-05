enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

apply { from("build-logic/common-settings.gradle.kts") }

// TODO вынести в общий код
dependencyResolutionManagement {
    versionCatalogs {
        create("vsCoreLibs") {
            from(files("../vs-core/libs.versions.toml"))
        }
    }
}
includeBuild("../vs-core")

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

include(":core:autoload")
include(":core:collections:tree")
include(":core:compose")
include(":core:compose-test")
include(":core:coroutines")
include(":core:coroutines-test")
include(":core:database:api")
include(":core:database:impl")
include(":core:decompose")
include(":core:di")
include(":core:factory-generator:api")
include(":core:factory-generator:ksp")
include(":core:fs:api")
include(":core:fs:impl")
include(":core:id")
include(":core:ksp")
include(":core:ktor:client")
include(":core:ktor:server")
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
include(":core:properties:impl")
include(":core:serialization:json")
include(":core:serialization:protobuf")
include(":core:shared-element-transition")
include(":core:splash")
include(":core:uikit:graph")
include(":core:uikit:icons")
include(":core:uikit:paddings")
include(":core:utils")

include("rsub:client")
include("rsub:connector:ktor-websocket:client")
include("rsub:connector:ktor-websocket:core")
include("rsub:connector:ktor-websocket:server")
include("rsub:core")
include("rsub:ksp:client")
include("rsub:ksp:server")
include("rsub:playground")
include("rsub:server")
include("rsub:test")
include("rsub:test-interface")

sharedFeature("auth")
sharedFeature("entities")
sharedFeature("server-info")

clientFeature("app-info")
clientFeature("debug-screen")
clientFeature("embedded-server")
clientFeature("initialization")
clientFeature("initialized-root-screen")
clientFeature("main-screen")
clientFeature("navigation-root-screen")
clientFeature("root-content-screen")
clientFeature("root-screen")
clientFeature("servers")
clientFeature("settings-screen")
clientFeature("splash-screen")
clientFeature("welcome-screen")

serverFeature("rsub")

include("client")

include(":server:common")
include(":server:embedded")
include(":server:standalone")

/**
 * Подключает полную иерархию фичей клиент + сервер + shared, согласно стандартному набору модулей.
 * @param name имя фичи.
 */
fun sharedFeature(name: String) {
    include(":feature:$name:shared-api")
    include(":feature:$name:shared-impl")
    clientFeature(name)
    serverFeature(name)
}

/**
 * Подключает иерархию фичи клиента, согласно стандартному набору модулей.
 * @param name имя фичи.
 */
fun clientFeature(name: String) {
    include(":feature:$name:client-api")
    include(":feature:$name:client-impl")
}

/**
 * Подключает иерархию фичи сервера, согласно стандартному набору модулей.
 * @param name имя фичи.
 */
fun serverFeature(name: String) {
    include(":feature:$name:server-api")
    include(":feature:$name:server-impl")
}
