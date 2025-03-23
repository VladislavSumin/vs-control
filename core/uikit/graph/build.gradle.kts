plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // TODO добавлять через каталог версий
            implementation("ru.vladislavsumin.core.collections:tree")
            implementation(projects.core.compose)
        }
    }
}
