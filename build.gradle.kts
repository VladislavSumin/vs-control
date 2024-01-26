plugins {
    id("ru.vs.convention.analyze.detekt")
}

tasks.register("ci") {
    doLast {
        println("CI hello world")
    }
}
