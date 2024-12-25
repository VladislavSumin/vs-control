plugins {
    id("ru.vs.convention.ksp-code-generator")
}

dependencies {
    implementation(projects.rsub.client)
    implementation(projects.core.coroutines)
    implementation(libs.kotlin.serialization.protobuf)
}
