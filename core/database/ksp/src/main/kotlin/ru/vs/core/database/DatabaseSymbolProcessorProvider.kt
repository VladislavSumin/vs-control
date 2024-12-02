package ru.vs.core.database

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import kotlin.io.path.Path

class DatabaseSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val projectRoot = environment.options["ru.vs.core.database.projectRoot"]
        check(projectRoot != null) { "Required params not provided" }
        return DatabaseSymbolProcessor(environment.codeGenerator, Path(projectRoot))
    }
}
