plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.vs.core.navigation.api)
            // TODO добавлять через каталог версий
            implementation("ru.vladislavsumin.core.collections:tree")
            implementation(projects.core.compose)
            implementation(projects.core.decompose)
            implementation(projects.core.di)
            implementation(libs.vs.core.logger.api)
            implementation(projects.core.serialization.json)
            implementation(projects.core.uikit.graph)
            implementation(projects.core.uikit.paddings)
            implementation(projects.core.utils)
        }
    }
}
