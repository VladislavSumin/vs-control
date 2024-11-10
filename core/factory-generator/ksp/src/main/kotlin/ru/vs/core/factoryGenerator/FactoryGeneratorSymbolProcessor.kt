package ru.vs.core.factoryGenerator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toAnnotationSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.vs.core.ksp.primaryConstructorWithPrivateFields
import ru.vs.core.ksp.processAnnotated
import ru.vs.core.ksp.writeTo

internal class FactoryGeneratorSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> =
        resolver.processAnnotated<GenerateFactory>(::processGenerateFactoryAnnotation)

    private fun processGenerateFactoryAnnotation(instance: KSAnnotated) {
        check(instance is KSClassDeclaration) { "Only KSClassDeclaration supported, but $instance was received" }
        val primaryConstructor = instance.primaryConstructor
        checkNotNull(primaryConstructor) { "For generate factory class must have primary constructor" }
        generateFactory(instance)
    }

    /**
     * Создает фабрику для создания экземпляров [instance] с одним методом create.
     *
     * @param instance инстанс который должна создавать фабрика
     */
    private fun generateFactory(
        instance: KSClassDeclaration,
    ) {
        // Имя будущей фабрики.
        val name = instance.simpleName.getShortName() + "Factory"

        // Список параметров в основном конструкторе.
        val constructorParams = instance.primaryConstructor!!.parameters

        val factoryConstructorParams =
            constructorParams
                .filter {
                    it.annotations
                        .map { it.toAnnotationSpec().typeName }
                        .none { it == BY_CREATE_ANNOTATION }
                }

        val functionParams = constructorParams - factoryConstructorParams

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
        val createFunction = FunSpec.builder("create")
            .apply {
                functionParams.forEach {
                    addParameter(it.name!!.getShortName(), it.type.toTypeName())
                }
            }
            .addCode(returnCodeBlock)
            .returns(instance.toClassName())
            .build()

        TypeSpec.classBuilder(name)
            .primaryConstructorWithPrivateFields(
                factoryConstructorParams.map { it.name!!.getShortName() to it.type.toTypeName() },
            )
            .addModifiers(KModifier.INTERNAL)
            .addFunction(createFunction)
            .build()
            .writeTo(codeGenerator, instance.packageName.asString())
    }

    companion object {
        private val BY_CREATE_ANNOTATION = ClassName("ru.vs.core.factoryGenerator", "ByCreate")
    }
}
