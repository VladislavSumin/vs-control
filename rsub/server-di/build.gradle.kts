plugins {
    id("ru.vs.convention.kmp.ktor")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.rsub.server)
            implementation(projects.core.di)
        }
    }
}
