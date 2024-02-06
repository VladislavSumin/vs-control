plugins {
    id("ru.vs.convention.kmp.js")
    id("ru.vs.convention.compose")
}

compose.experimental.web.application {}

kotlin {
    js {
        binaries.executable()

        browser {
            commonWebpackConfig {
                outputFileName = "main.js"
            }
        }
    }
    sourceSets {
        jsMain.dependencies {
            implementation(projects.client.common)
        }
    }
}
