plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(vsCoreLibs.kotlin.coroutines.test)
        }
    }
}
