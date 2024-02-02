plugins {
    id("ru.vs.convention.kmp.js")
}

kotlin {
    js(IR) {
        binaries.executable()
    }
//    sourceSets {
//        jvmMain.dependencies {
//            implementation(projects.client.common)
//        }
//    }
}
