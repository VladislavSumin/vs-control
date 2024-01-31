plugins {
    id("ru.vs.convention.kmp.jvm")
}

kotlin {
    sourceSets {
        jvmMain {
            dependencies {
                implementation(projects.core.logger.api)
                implementation(projects.core.logger.platform)
            }
        }
    }
}
