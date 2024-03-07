plugins {
    id("kotlin")
    id("ru.vs.convention.context-receivers")
}

dependencies {
    compileOnly(libs.detekt.api)
    runtimeOnly(libs.detekt.cli)
}
