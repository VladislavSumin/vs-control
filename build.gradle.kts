plugins {
    id("ru.vs.convention.analyze.detekt-build-logic")
}

// Настраиваем детект для всех модулей
allprojects {
    apply {
        plugin("ru.vs.convention.analyze.detekt")
    }
}

tasks.register("ci") {
    doLast {
        println("CI hello world")
    }
}
