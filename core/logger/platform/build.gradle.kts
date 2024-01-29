plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.logger.manager)
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.logging.log4j.api)
                implementation(libs.logging.log4j.core)
            }
        }
    }
}
