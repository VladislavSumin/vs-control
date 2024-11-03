import ru.vs.utils.libs

plugins {
    kotlin("jvm")
}

dependencies {
    api(libs.kotlin.ksp)
    api(libs.kotlinpoet.core)
    api(libs.kotlinpoet.ksp)
    api(projects.core.factoryGenerator.api)
}
