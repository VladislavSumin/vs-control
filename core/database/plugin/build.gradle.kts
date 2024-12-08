plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("database-plugin") {
            id = "ru.vs.core.database"
            implementationClass = "ru.vs.core.database.plugin.DatabasePlugin"
        }
    }
}

dependencies {
    implementation(libs.kotlinpoet.core)
    implementation(libs.gradlePlugins.kotlin.core)
}
