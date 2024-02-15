plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.logger.api)
            implementation(projects.core.logger.platform)

            api(projects.core.compose)
            api(projects.core.di)

            // Features
            api(projects.feature.appInfo.clientImpl)
        }
    }
}
