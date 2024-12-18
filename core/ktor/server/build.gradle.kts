plugins {
    id("ru.vs.convention.kmp.ktor")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.ktor.server.core)
            implementation(projects.core.di)
        }
    }
}
