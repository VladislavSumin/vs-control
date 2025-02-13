plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(vsCoreLibs.kotlin.coroutines.core)

            implementation(projects.core.di)
        }
    }
}
