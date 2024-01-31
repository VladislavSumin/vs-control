plugins {
    id("ru.vs.convention.kmp.jvm")
}

kotlin {
    sourceSets {
        jvmMain.dependencies {
            implementation(projects.client.common)
        }
    }
}
