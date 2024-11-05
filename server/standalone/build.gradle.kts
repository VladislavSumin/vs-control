plugins {
    id("ru.vs.convention.kmp.jvm")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.server.common)
            implementation(projects.core.logger.platform)
        }
    }
}
