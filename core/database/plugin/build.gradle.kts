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
