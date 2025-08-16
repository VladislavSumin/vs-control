package ru.vs.convention

import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

/**
 * Hack from https://github.com/google/ksp/issues/567#issuecomment-1510477456
 *
 * By default, KSP processing sources separate by targets, if we want to generate sources for common module we
 * need special hacky configuration.
 *
 * After apply this convention add ksp plugins as
 *
 * dependencies  {
 *   add("kspCommonMainMetadata", <your-ksp-dependency>)
 * }
 *
 */

plugins {
    id("kotlin-multiplatform")
    id("com.google.devtools.ksp")
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}
