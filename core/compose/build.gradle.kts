plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Основной compose runtime, не тянет за собой ничего лишнего, только сам runtime
            api(compose.runtime)
        }

        jvmMain.dependencies {
            // Тянет набор библиотек для текущей платформы.
            // Тянет за собой кучу других compose библиотек, в том числе material.
            // Эта библиотека подключает специфичные апи заточенные под конкретную OS и конкретную архитектуру,
            // поэтому данный код не запустится на других платформах, кроме той на которой его собрали.
            // TODO сделать разделение по платформам.
            api(compose.desktop.currentOs)
        }
    }
}