plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.server.common)
            implementation(projects.core.logger.platform)
        }
    }
}
