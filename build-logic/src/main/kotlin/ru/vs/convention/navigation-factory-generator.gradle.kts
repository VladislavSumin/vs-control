package ru.vs.convention

import ru.vladislavsumin.utils.vsCoreLibs

/**
 * Настройка по умолчанию для подключения генерации фабрик из модуля navigation:factory-generator
 */

plugins {
    id("kotlin-multiplatform")
    id("ru.vladislavsumin.convention.kmp.ksp-hack")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(vsCoreLibs.vs.core.navigation.factoryGenerator.api)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", vsCoreLibs.vs.core.navigation.factoryGenerator.ksp)
}
