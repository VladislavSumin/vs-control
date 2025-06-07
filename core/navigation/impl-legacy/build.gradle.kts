plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        all {
            languageSettings.optIn("ru.vladislavsumin.core.navigation.InternalNavigationApi")
        }

        commonMain.dependencies {
            api(libs.vs.core.navigation.api)
            api(libs.vs.core.navigation.impl)
            implementation(libs.vs.core.collections.tree)
            implementation(projects.core.compose)
            implementation(projects.core.decompose)
            implementation(projects.core.di)
            implementation(libs.vs.core.logger.api)
            implementation(projects.core.serialization.json)
            implementation(libs.vs.core.uikit.graph)
            implementation(libs.vs.core.navigation.debug)
            implementation(projects.core.uikit.paddings)
            implementation(projects.core.utils)
        }
    }
}
