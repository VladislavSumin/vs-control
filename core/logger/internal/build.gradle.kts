plugins {
    id("ru.vs.convention.kmp.jvm")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.logger.common)
            }
        }
    }
}
