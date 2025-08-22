package ru.vs.rsub.ksp.server

import com.google.devtools.ksp.findActualType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.vladislavsumin.core.ksp.utils.Types
import ru.vladislavsumin.core.ksp.utils.writeTo
import ru.vs.rsub.RSubServerInterface
import ru.vs.rsub.RSubServerSubscription

class RSubProxyGenerator(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) {
    fun generateWrappers(impls: List<KSType>, originatingKSFile: KSFile) {
        impls.forEach { generateWrapper(it, originatingKSFile) }
    }

    private fun generateWrapper(impl: KSType, originatingKSFile: KSFile) {
        val nameProperty = PropertySpec.builder("rSubName", String::class.asClassName(), KModifier.OVERRIDE)
            .initializer("%S", impl.toClassName().simpleName)
            .build()
        val mapPropery = PropertySpec.builder(
            "rSubSubscriptions",
            Map::class.asClassName().parameterizedBy(
                String::class.asClassName(),
                RSubServerSubscription::class.asClassName(),
            ),
            KModifier.OVERRIDE,
        )
            .initializer(
                CodeBlock.builder()
                    .addStatement("mapOf(")
                    .apply {
                        generateInitializers(impl)
                    }
                    .addStatement(")")
                    .build(),
            )
            .build()
        return TypeSpec.classBuilder(impl.toClassName().simpleName + NAME_POSTFIX)
            .addModifiers(KModifier.INTERNAL)
            .primaryConstructor(generateConstructor(impl))
            .addProperty(nameProperty)
            .addProperty(mapPropery)
            .addSuperinterface(RSubServerInterface::class)
            .addOriginatingKSFile(originatingKSFile)
            .build()
            .writeTo(codeGenerator, impl.toClassName().packageName)
    }

    private fun generateConstructor(impl: KSType): FunSpec {
        return FunSpec.constructorBuilder()
            .addParameter(PARAM_NAME, impl.toTypeName())
            .build()
    }

    private fun CodeBlock.Builder.generateInitializers(impl: KSType): CodeBlock.Builder {
        (impl.declaration as KSClassDeclaration)
            .getAllFunctions()
            .filter { it.isAbstract }
            .forEach { generateInitializer(it) }
        return this
    }

    private fun CodeBlock.Builder.generateInitializer(method: KSFunctionDeclaration) {
        when {
            method.modifiers.contains(Modifier.SUSPEND) -> generateInitializerTyped(createSuspend, method)
            (method.returnType!!.resolve().toTypeName() as? ParameterizedTypeName)
                ?.rawType == Types.Coroutines.Flow -> generateInitializerTyped(createFlow, method)

            else -> {
                logger.error("Cannot generate wrapper for this function", method)
            }
        }
    }

    private fun CodeBlock.Builder.generateInitializerTyped(wrapperFunction: MemberName, method: KSFunctionDeclaration) {
        val methodName = method.simpleName.asString()
        this
            .addStatement(
                "%S to %M(",
                methodName,
                wrapperFunction,
            )
            .apply {
                addStatement("listOf(")
                method.parameters.forEach {
                    val className = when (val type = it.type.resolve()) {
                        is KSTypeAlias -> type.findActualType().toClassName()
                        else -> type.toTypeName()
                    }
                    addStatement("%M<%T>(),", typeOfMember, className)
                }
                addStatement(")")
            }
            .beginControlFlow(")")
            .addStatement(
                "arguments -> %L.%L(",
                PARAM_NAME,
                methodName,
            )
            .apply {
                method.parameters
                    .map {
                        when (val type = it.type.resolve()) {
                            is KSTypeAlias -> type.findActualType().toClassName()
                            else -> type.toTypeName()
                        }
                    }
                    .forEachIndexed { index, className ->
                        addStatement("arguments[%L] as %T,", index.toString(), className)
                    }
            }
            .addStatement(")")
            .endControlFlow()
            .addStatement(",")
    }

    companion object {
        private const val NAME_POSTFIX = "RSubServerProxy"
        private const val PARAM_NAME = "origin"
        private val createSuspend = RSubServerSubscription::class.asClassName()
            .nestedClass("Companion")
            .member("createSuspend")
        private val createFlow = RSubServerSubscription::class.asClassName()
            .nestedClass("Companion")
            .member("createFlow")
        private val typeOfMember = MemberName("kotlin.reflect", "typeOf")
    }
}
