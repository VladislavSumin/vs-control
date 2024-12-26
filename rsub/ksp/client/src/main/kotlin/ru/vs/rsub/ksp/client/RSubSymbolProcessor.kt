package ru.vs.rsub.ksp.client

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.vs.core.ksp.processAnnotated
import ru.vs.rsub.RSubClientInterfaces

class RSubSymbolProcessor(
    codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    private val proxyGenerator = RSubInterfaceProxyGenerator(logger, codeGenerator)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        return resolver.processAnnotated<RSubClientInterfaces> {
            val impls = it
                .annotations
                .first { it.annotationType.toTypeName() == RSubClientInterfaces::class.asTypeName() }
                .arguments
                .first()
                .value!! as List<KSType>

            val classes = impls.map { resolver.getClassDeclarationByName(it.declaration.qualifiedName!!)!! }

            proxyGenerator.generateProxies(classes)
        }
    }
}
