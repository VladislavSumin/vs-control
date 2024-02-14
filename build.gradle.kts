plugins {
    id("ru.vs.convention.analyze.detekt-build-logic")
    id("ru.vs.convention.analyze.check-module-graph")
}

// TODO убрать allprojects переместить в convention для модулей
// Настраиваем детект для всех модулей
allprojects {
    apply {
        plugin("ru.vs.convention.analyze.detekt")
    }
}
