plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // TODO добавлять через каталог версий
            api("ru.vladislavsumin.core.decompose:components")
            // TODO добавлять через каталог версий
            api("ru.vladislavsumin.core.decompose:compose")

            api(vsCoreLibs.decompose.core)
            api(vsCoreLibs.decompose.extensions.compose)
            api(vsCoreLibs.decompose.extensions.composeExperimental)

            // TODO вынести в другой модуль?
            api(vsCoreLibs.vs.core.navigation.impl)
            api(vsCoreLibs.vs.core.navigation.di)
            // TODO вынести в другой модуль?
            implementation(vsCoreLibs.vs.core.di)

            implementation(projects.core.compose)
            implementation(projects.core.serialization.json)
            implementation(projects.core.utils)
        }
        androidMain.dependencies {
            api(vsCoreLibs.decompose.extensions.android)
        }
        commonTest.dependencies {
            // TODO
            implementation("ru.vladislavsumin.core.decompose:test")
        }
    }
}
