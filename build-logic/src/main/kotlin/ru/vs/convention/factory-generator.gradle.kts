package ru.vs.convention

import ru.vs.utils.libs

/**
 * Настройка по умолчанию для подключения генерации фабрик из модуля factory-generator
 */

plugins {
    id("kotlin-multiplatform")
    id("ru.vs.convention.ksp-kmp-hack")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.vs.core.factoryGenerator.api)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.vs.core.factoryGenerator.ksp)
}
