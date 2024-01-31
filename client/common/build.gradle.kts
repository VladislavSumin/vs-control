plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.logger.api)
            implementation(projects.core.logger.platform)
        }
    }
}
