package ru.vs.core.factoryGenerator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

internal class FactoryGeneratorSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        return resolver.getSymbolsWithAnnotation(GenerateFactory::class.qualifiedName!!)
            .filterNot(this::processAnnotated)
            .toList()
    }

    private fun processAnnotated(annotated: KSAnnotated): Boolean {
        return try {
            processGenerateFactoryAnnotation(annotated)
            true
        } catch (_: IllegalArgumentException) {
            // We have cases when one generated factory using inside another generated factory,
            // for these cases we need to processing sources with more than once iteration
            false
        } catch (e: Exception) {
            logger.exception(e)
            false
        }
    }

    private fun processGenerateFactoryAnnotation(instance: KSAnnotated) {
        check(instance is KSClassDeclaration) {
            "Only KSClassDeclaration supported, but $instance was received"
        }
        val primaryConstructor = instance.primaryConstructor
        checkNotNull(primaryConstructor)

        val factoryInterface = instance.annotations
            .first { it.annotationType.toTypeName() == GenerateFactory::class.asTypeName() }
            .arguments.first().value as KSType?

//        if (factoryInterface == null) {
        generateFactoryInterfaceAndImpl(instance)
//        } else {
//            generateFactoryImplForFactoryInterface(factoryInterface, instance)
//        }
    }

    /**
     * Generate factory implementation by given [factoryInterface] && [instance]
     */
    private fun generateFactoryImplForFactoryInterface(
        factoryInterface: KSType,
        instance: KSClassDeclaration,
    ) {
        val factoryInterfaceDeclaration = factoryInterface.declaration as KSClassDeclaration
        val factoryInterfaceClassName = factoryInterface.toClassName()
        val factoryImplementationName = factoryInterfaceClassName.simpleName + "Impl"

        // Find single abstract method of factory interface
        val factoryMethod: KSFunctionDeclaration = factoryInterfaceDeclaration.getAllFunctions()
            .filter { it.isAbstract }
            .single()

        // Factory abstract methods arguments
        val factoryMethodParameters = factoryMethod.parameters

        val instancePrimaryConstructor = instance.primaryConstructor!!

        // Add here parameters not defined by factory methods function arguments
        val nonProvidedByFactoryMethodsParams = mutableListOf<KSValueParameter>()

        val codeBlock = CodeBlock.builder()
            .add("return %T(", instance.toClassName())
            .apply {
                val factoryMethodParametersCache = factoryMethodParameters
                    .associateBy { it.name!!.getShortName() }
                    .toMutableMap()

                instancePrimaryConstructor.parameters.forEach { parameter ->
                    if (factoryMethodParametersCache.remove(parameter.name!!.getShortName()) == null) {
                        nonProvidedByFactoryMethodsParams.add(parameter)
                    }

                    add("%L, ", parameter.name!!.getShortName())
                }

                check(factoryMethodParametersCache.isEmpty())
            }
            .add(")")
            .build()

        val constructor = FunSpec.constructorBuilder()
            .apply {
                nonProvidedByFactoryMethodsParams.forEach {
                    addParameter(it.name!!.getShortName(), it.type.toTypeName())
                }
            }
            .build()

        val function = FunSpec.builder(factoryMethod.simpleName.getShortName())
            .addModifiers(KModifier.OVERRIDE)
            .addParameters(
                factoryMethodParameters.map {
                    ParameterSpec(it.name!!.getShortName(), it.type.toTypeName())
                },
            )
            .addCode(codeBlock)
            .returns(factoryMethod.returnType!!.toTypeName())
            .build()

        val clazz = TypeSpec.classBuilder(factoryImplementationName)
            .addModifiers(KModifier.INTERNAL)
            .primaryConstructor(constructor)
            .addSuperinterface(factoryInterfaceClassName)
            .apply {
                nonProvidedByFactoryMethodsParams.forEach {
                    val name = it.name!!.getShortName()
                    addProperty(
                        PropertySpec.builder(name, it.type.toTypeName())
                            .initializer(name)
                            .addModifiers(KModifier.PRIVATE)
                            .build(),
                    )
                }
            }
            .addFunction(function)
            .build()

        FileSpec.builder(
            factoryInterfaceClassName.packageName,
            factoryImplementationName,
        )
            .addType(clazz)
            .build()
            .writeTo(codeGenerator, false)
    }

    /**
     * Generate factory interface for [instance] with one method named [createMethodName].
     * Interface name is [instance] name + [suffix]
     *
     * Generate factory implementation for interface above with name [instance] name + [suffix] + [implSuffix]
     *
     * @param instance instance for creation
     * @param suffix factory name suffix
     * @param implSuffix factory impl name suffix
     * @param createMethodName name for create [instance] method
     */
    private fun generateFactoryInterfaceAndImpl(
        instance: KSClassDeclaration,
        suffix: String = "Factory",
        implSuffix: String = "Impl",
        createMethodName: String = "create",
    ) {
        val name = instance.simpleName.getShortName() + suffix

        val codeBlock = CodeBlock.builder()
            .add("return %T(", instance.toClassName())
            .apply {
                instance.primaryConstructor!!.parameters.forEach { parameter ->
                    add("%L, ", parameter.name!!.getShortName())
                }
            }
            .add(")")
            .build()

        // Generate "create" function with parameters matches instance primary constructor
        val function = FunSpec.builder(createMethodName)
            .addModifiers(KModifier.ABSTRACT)
            .returns(instance.toClassName())
            .build()
        val functionImpl = FunSpec.builder(createMethodName)
            .addModifiers(KModifier.OVERRIDE)
            .addCode(codeBlock)
            .returns(instance.toClassName())
            .build()

        // Generate constructor
        val constructor = FunSpec.constructorBuilder()
            .apply {
                instance.primaryConstructor!!.parameters.forEach {
                    addParameter(it.name!!.getShortName(), it.type.toTypeName())
                }
            }
            .build()

        // Generate class
        val clazz = TypeSpec.interfaceBuilder(name)
            .addModifiers(KModifier.INTERNAL)
            .addFunction(function)
            .build()
        // Generate class impl
        val clazzImpl = TypeSpec.classBuilder(name + implSuffix)
            .primaryConstructor(constructor)
            .addSuperinterface(ClassName(instance.packageName.asString(), name))
            .addModifiers(KModifier.INTERNAL)
            .apply {
                instance.primaryConstructor!!.parameters.forEach {
                    val name = it.name!!.getShortName()
                    addProperty(
                        PropertySpec.builder(name, it.type.toTypeName())
                            .initializer(name)
                            .addModifiers(KModifier.PRIVATE)
                            .build(),
                    )
                }
            }
            .addFunction(functionImpl)
            .build()

        // Generate kotlin file
        FileSpec.builder(
            instance.packageName.asString(),
            name,
        )
            .addType(clazz)
            .build()
            .writeTo(codeGenerator, false)

        FileSpec.builder(
            instance.packageName.asString(),
            name + implSuffix,
        )
            .addType(clazzImpl)
            .build()
            .writeTo(codeGenerator, false)
    }
}
