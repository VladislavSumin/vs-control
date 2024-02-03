plugins {
    id("ru.vs.convention.android.application")
    id("ru.vs.convention.compose")
}

android {
    namespace = "ru.vs.control"

    defaultConfig {
        applicationId = "ru.vs.control"
    }
}

dependencies {
    implementation(projects.client.common)
}
