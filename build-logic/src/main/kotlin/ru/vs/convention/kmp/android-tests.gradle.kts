package ru.vs.convention.kmp

/**
 * Дополнительная настройка unit тестов для android таргета.
 */

plugins {
    id("kotlin-multiplatform")
}

kotlin {
    // Мы должны вызвать androidTarget() перед доступом к sourceSets, иначе нужные sourceSets созданы не будут.
    androidTarget()
    sourceSets {
        named("androidUnitTest") {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
    }
}
