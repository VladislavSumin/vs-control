plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.fs.api)
            implementation(vsCoreLibs.vs.core.di)
        }
    }
}
