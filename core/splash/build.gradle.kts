plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.compose)
            implementation(projects.core.coroutines)
            implementation(projects.core.decompose)
        }
        commonTest.dependencies {
            implementation(projects.core.composeTest)
            implementation(projects.core.coroutinesTest)
            implementation(projects.core.decomposeTest)
        }
    }
}
