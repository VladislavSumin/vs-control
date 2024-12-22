plugins {
    id("ru.vs.convention.ksp-code-generator")
}

dependencies {
    implementation(projects.rsub.client)

    // implementation(coreLibs.vs.core.coroutines)
    // implementation(coreLibs.kotlin.serialization.core)
    // implementation(coreLibs.kotlin.serialization.json)
}
