plugins {
    id("ru.vs.convention.android.application")
}

android {
    namespace = "ru.vs.control"

    defaultConfig {
        applicationId = "ru.vs.control"
    }
}

dependencies {
    implementation(projects.core.logger.api)
    implementation(projects.core.logger.platform)
}
