plugins {
    id("ru.vs.convention.ksp-code-generator")
}

dependencies {
    implementation(projects.rsub.server)
    implementation(projects.core.utils)
}
