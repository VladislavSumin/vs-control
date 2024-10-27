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
            implementation(projects.core.coroutinesTest)
        }
    }
}

// Экспериментальная попытка заигнорить тесты проблемные тесты в андроиде.
android {
    testOptions {
        unitTests {
            all {
                it.exclude("**/*Ui.kt")
            }
        }
    }
}