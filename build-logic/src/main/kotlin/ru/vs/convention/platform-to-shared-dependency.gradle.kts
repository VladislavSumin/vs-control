package ru.vs.convention

/**
 * Автоматически прописывает платформенным модулям зависимость на shared модули.
 */

plugins {
    id("kotlin-multiplatform")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Ищем суффикс текущего проекта api/impl
            val dividerIndex = project.name.lastIndexOf("-")
            check(dividerIndex > 0) { "Wrong project name" }
            val suffix = project.name.substring(dividerIndex, project.name.length)

            val sharedProjectName = "shared$suffix"
            val sharedProject = project.parent!!.findProject(sharedProjectName)
            if (sharedProject != null) {
                if (suffix == "-api") {
                    api(sharedProject)
                } else {
                    implementation(sharedProject)
                }
            }
        }
    }
}
