package ru.vs.core.database

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import java.nio.file.Path
import kotlin.io.path.relativeTo
import kotlin.io.path.walk

@Suppress("UnusedPrivateProperty")
internal class DatabaseSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val projectRoot: Path,
) : SymbolProcessor {
    // TODO почему то этот метод вызывается три раза на каждый модуль, нужно разобраться.
    override fun process(resolver: Resolver): List<KSAnnotated> {
        // Проходимся по всем объявлениям таблиц sqldelight и для каждой queries генерируем свой провайдер.
        val sqldelightPath = projectRoot.resolve("src/commonMain/sqldelight")
        sqldelightPath.walk()
            .map { it.relativeTo(sqldelightPath).toString() }
            .forEach(::generateProvider)

        return emptyList()
    }

    private fun generateProvider(sqlClass: String) {

    }
}
