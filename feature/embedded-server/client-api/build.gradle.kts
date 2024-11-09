plugins {
    id("ru.vs.convention.preset.feature-client-api-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Нужна в api модуле так как интерфейс компонента реализует функцию расширение над LazyListScope
            implementation(projects.core.compose)
        }
    }
}
