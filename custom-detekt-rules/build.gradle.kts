plugins {
    id("kotlin")
}

dependencies {
    compileOnly(libs.detekt.api)
    runtimeOnly(libs.detekt.cli)
}
