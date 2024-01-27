plugins {
    id("ru.vs.convention.kmp.jvm")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.logger.api)
                implementation(projects.core.logger.internal)
            }
        }
    }
}