package ru.vs.rsub.ksp.server

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.vs.core.ksp.processAnnotated
import ru.vs.core.ksp.writeTo
import ru.vs.core.utils.decapitalized
import ru.vs.rsub.RSubServerSubscriptions
import ru.vs.rsub.RSubServerSubscriptionsAbstract

class RSubSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    logger: KSPLogger,
) : SymbolProcessor {
    private val subscriptionWrapperGenerator = RSubSubscriptionWrapperGenerator(logger)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        return resolver.processAnnotated<RSubServerSubscriptions> {
            check(it is KSClassDeclaration) { "Only KSClassDeclaration supported, but $it was received" }
            generateSubscriptions(it)
        }
    }

    private fun generateSubscriptions(subscriptions: KSClassDeclaration) {
        @Suppress("UNCHECKED_CAST")
        val impls = subscriptions
            .annotations
            .first { it.annotationType.toTypeName() == RSubServerSubscriptions::class.asTypeName() }
            .arguments
            .first()
            .value!! as List<KSType>

        val name = subscriptions.simpleName.asString() + "Impl"

        TypeSpec.classBuilder(name)
            .addOriginatingKSFile(subscriptions.containingFile!!)
            .addModifiers(KModifier.INTERNAL)
            .superclass(RSubServerSubscriptionsAbstract::class)
            .addSuperinterface(subscriptions.toClassName())
            .primaryConstructor(generateConstructor(impls))
            .addTypes(subscriptionWrapperGenerator.generateWrappers(impls))
            .build()
            .writeTo(codeGenerator, subscriptions.packageName.asString())
    }

    private fun generateConstructor(impls: List<KSType>): FunSpec {
        val params = impls.map {
            val implClassName = it.toClassName()
            ParameterSpec.builder(implClassName.simpleName.decapitalized(), implClassName).build()
        }
        return FunSpec.constructorBuilder()
            .addParameters(params)
            .addCode(generateInitializer(impls))
            .build()
    }

    private fun generateInitializer(impls: List<KSType>): CodeBlock {
        val builder = CodeBlock.builder()
        impls.forEach { builder.generateInitializer(it) }
        return builder.build()
    }

    private fun CodeBlock.Builder.generateInitializer(implType: KSType) {
        val implName = implType.toClassName().simpleName
        addStatement(
            "impls[%S] = %T(%L)",
            implName,
            ClassName("", implName + "Wrapper"),
            implName.decapitalized(),
        )
    }
}
