plugins {
    id("ru.vs.convention.kmp.jvm")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.logger.api)
                implementation(projects.core.logger.manager)
            }
        }
    }
}
