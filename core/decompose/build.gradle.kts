plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // TODO добавлять через каталог версий
            api("ru.vladislavsumin.core.decompose:components")

            api(libs.decompose.core)
            api(libs.decompose.extensions.compose)
            api(libs.decompose.extensions.composeExperimental)

            implementation(projects.core.compose)
            implementation(projects.core.serialization.json)
            implementation(projects.core.utils)
        }
        androidMain.dependencies {
            api(libs.decompose.extensions.android)
        }
    }
}
