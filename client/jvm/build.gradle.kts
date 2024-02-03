plugins {
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        jvmMain.dependencies {
            implementation(projects.client.common)
        }
    }
}
