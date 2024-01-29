plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.logger.common)
                implementation(projects.core.logger.internal)
            }
        }
    }
}
