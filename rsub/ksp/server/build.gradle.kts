plugins {
    id("ru.vs.convention.ksp-code-generator")
}

dependencies {
    implementation(projects.rsub.server)

    implementation(coreLibs.vs.core.coroutines)
    implementation(coreLibs.vs.core.utils)
    implementation(coreLibs.kotlin.serialization.core)
    implementation(coreLibs.kotlin.serialization.json)
}
