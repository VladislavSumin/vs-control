package ru.vs.core.database

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import java.nio.file.Path

@Suppress("UnusedPrivateProperty")
internal class DatabaseSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val projectRoot: Path,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        return emptyList()
    }
}
