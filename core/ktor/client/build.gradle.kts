plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(projects.core.di)
        }
    }
}
