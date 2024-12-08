package ru.vs.core.database.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class DatabasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("QWQW Hello from database plugin")
    }
}
