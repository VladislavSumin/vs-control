package ru.vs.core.factoryGenerator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

internal class FactoryGeneratorSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // Обходим все аннотированные GenerateFactory классы.
        // TODO описать подробно механику возврата в этом методе.
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
        } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
            logger.exception(e)
            false
        }
    }

    private fun processGenerateFactoryAnnotation(instance: KSAnnotated) {
        check(instance is KSClassDeclaration) { "Only KSClassDeclaration supported, but $instance was received" }
        val primaryConstructor = instance.primaryConstructor
        checkNotNull(primaryConstructor) { "For generate factory class must have primary constructor" }
        generateFactory(instance)
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
    private fun generateFactory(
        instance: KSClassDeclaration,
        suffix: String = "Factory",
        createMethodName: String = "create",
    ) {
        // Имя будущей фабрики.
        val name = instance.simpleName.getShortName() + suffix

        // Список параметров в основном конструкторе.
        val constructorParams = instance.primaryConstructor!!.parameters

        // Блок кода который будет помещен в тело функции crate
        // return InstanceName(param1, param2)
        val returnCodeBlock = CodeBlock.builder()
            .add("return %T(", instance.toClassName())
            .apply {
                constructorParams.forEach { parameter ->
                    add("%L, ", parameter.name!!.getShortName())
                }
            }
            .add(")")
            .build()

        // Декларация функции create
        val createFunction = FunSpec.builder(createMethodName)
            .addCode(returnCodeBlock)
            .returns(instance.toClassName())
            .build()

        // Конструктор фабрики, обратите внимание, нельзя объявить сразу конструктор с пропертями + полями класса
        // поля класса отдельно объявляются ниже.
        val constructor = FunSpec.constructorBuilder()
            .apply {
                constructorParams.forEach {
                    addParameter(it.name!!.getShortName(), it.type.toTypeName())
                }
            }
            .build()

        // Generate class impl
        val clazzImpl = TypeSpec.classBuilder(name)
            .primaryConstructor(constructor)
            .addModifiers(KModifier.INTERNAL)
            .apply {
                constructorParams.forEach {
                    val name = it.name!!.getShortName()
                    addProperty(
                        PropertySpec.builder(name, it.type.toTypeName())
                            .initializer(name)
                            .addModifiers(KModifier.PRIVATE)
                            .build(),
                    )
                }
            }
            .addFunction(createFunction)
            .build()

        // Записываем полученную фабрику в файл
        FileSpec.builder(
            instance.packageName.asString(),
            name,
        )
            .addType(clazzImpl)
            .build()
            .writeTo(codeGenerator, false)
    }
}
