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
    implementation(vsCoreLibs.kotlinpoet.core)
    implementation(vsCoreLibs.gradlePlugins.kotlin.core)
    implementation(libs.gradlePlugins.sqldelight)
}
