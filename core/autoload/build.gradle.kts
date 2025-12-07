plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.coroutines)
            implementation(vsCoreLibs.vs.core.di)
        }
    }
}
