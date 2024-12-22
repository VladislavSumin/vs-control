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

android {
    testOptions {
        unitTests {
            all {
                // Compose тесты сейчас не работают в android(local) режиме.
                // TODO дождаться поддержки
                // https://slack-chats.kotlinlang.org/t/18784429/hi-there-i-m-trying-to-run-an-ui-test-in-shared-commontest-a
                it.exclude("**/*Ui*")
            }
        }
    }
}
