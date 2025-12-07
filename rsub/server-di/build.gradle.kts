plugins {
    id("ru.vs.convention.kmp.ktor")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.rsub.server)
            implementation(vsCoreLibs.vs.core.di)
        }
    }
}
