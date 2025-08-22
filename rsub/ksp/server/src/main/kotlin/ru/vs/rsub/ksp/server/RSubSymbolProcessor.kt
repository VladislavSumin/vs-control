package ru.vs.rsub.ksp.server

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.vladislavsumin.core.ksp.utils.processAnnotated
import ru.vs.rsub.RSubServerInterfaces

class RSubSymbolProcessor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
) : SymbolProcessor {
    private val proxyGenerator = RSubProxyGenerator(logger, codeGenerator)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        return resolver.processAnnotated<RSubServerInterfaces> {
            val impls = it
                .annotations
                .first { it.annotationType.toTypeName() == RSubServerInterfaces::class.asTypeName() }
                .arguments
                .first()
                .value!! as List<KSType>
            proxyGenerator.generateWrappers(impls, it as KSFile)
        }
    }
}
