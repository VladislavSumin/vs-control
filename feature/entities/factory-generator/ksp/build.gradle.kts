plugins {
    id("ru.vs.convention.ksp-code-generator")
}

dependencies {
    implementation(projects.feature.entities.factoryGenerator.api)
}
