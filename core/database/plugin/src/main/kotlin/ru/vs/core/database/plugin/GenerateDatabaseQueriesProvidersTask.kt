package ru.vs.core.database.plugin

import com.squareup.kotlinpoet.FileSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Генерирует *QueriesProvider.kt файлы.
 */
abstract class GenerateDatabaseQueriesProvidersTask : DefaultTask() {
    /**
     * Папка с файлами конфигурации sqldelight.
     */
    @InputDirectory
    val sourceDir = project.layout.projectDirectory.dir("src/commonMain/sqldelight")

    /**
     * Папка куда будет записан сгенерированный код.
     */
    @OutputDirectory
    val generatedCodeDir = project.layout.projectDirectory
        .dir("build/generated/databaseQueries/metadata/commonMain/kotlin")

    @TaskAction
    fun action() {
        generatedCodeDir.asFile.deleteRecursively()

        // Проходимся по всем sq файлам с объявлениями таблиц.
        val sqlRootDir = sourceDir.asFile
        sqlRootDir
            .walk()
            .filter { it.isFile }
            .map {
                it
                    .relativeTo(sqlRootDir)
                    .toString()
                    .dropLast(".sq".length)
                    .replace("/", ".")
            }
            .forEach(::generateQueriesProvider)
    }

    private fun generateQueriesProvider(recordClassFullName: String) {
        val classPackage = recordClassFullName.dropLastWhile { it != '.' }.dropLast(1)
        val className = recordClassFullName
            .takeLastWhile { it != '.' }
            .dropLast("Record".length) + "QueriesProvider"

        // Записываем полученный провайдер в файл
        FileSpec.builder(classPackage, className)
            .build()
            .writeTo(generatedCodeDir.asFile)
    }
}
