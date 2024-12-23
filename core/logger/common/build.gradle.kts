plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        jvmTest.dependencies {
            // TODO
            implementation(libs.testing.mockk)
        }
    }
}
