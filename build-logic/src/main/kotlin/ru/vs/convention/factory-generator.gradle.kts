package ru.vs.convention

import ru.vladislavsumin.utils.vsCoreLibs

/**
 * Настройка по умолчанию для подключения генерации фабрик из модуля factory-generator
 */

plugins {
    id("kotlin-multiplatform")
    id("ru.vladislavsumin.convention.kmp.ksp-hack")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(vsCoreLibs.vs.core.factoryGenerator.api)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", vsCoreLibs.vs.core.factoryGenerator.ksp)
}
