/**
 * Общая для проекта и build-logc часть settings.gradle.kts
 */

apply { from("../../vs-core/build-scripts/common-settings.gradle.kts") }

dependencyResolutionManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}