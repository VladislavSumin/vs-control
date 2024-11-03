plugins {
    id("ru.vs.convention.ksp-code-generator")
}

dependencies {
    implementation(projects.core.navigation.factoryGenerator.api)
}
