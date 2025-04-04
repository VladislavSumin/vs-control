plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.rsub.core)
            implementation(libs.ktor.websockets)
        }
    }
}
