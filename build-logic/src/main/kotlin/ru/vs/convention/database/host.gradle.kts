package ru.vs.convention.database

/**
 * Базовая настройка базы данных для хоста базы данных.
 */

plugins {
    id("kotlin-multiplatform")
    id("app.cash.sqldelight")
}

sqldelight {
    databases {
        register("Database") {
            // TODO тут хардкод пакета пока
            packageName.set("ru.vs.control.repository")
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:database:impl"))
        }
    }
}
