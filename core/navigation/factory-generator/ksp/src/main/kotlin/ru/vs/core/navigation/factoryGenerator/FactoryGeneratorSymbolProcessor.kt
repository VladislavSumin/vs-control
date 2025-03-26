package ru.vs.core.navigation.factoryGenerator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.vs.core.ksp.primaryConstructorWithPrivateFields
import ru.vs.core.ksp.processAnnotated
import ru.vs.core.ksp.writeTo

internal class FactoryGeneratorSymbolProcessor(
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> =
        resolver.processAnnotated<GenerateScreenFactory>(::processGenerateFactoryAnnotation)

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

        // TODO оптимизировать и задокументировать
        val paramsType: TypeName = constructorParams.find {
            (it.type.resolve().declaration as? KSClassDeclaration)
                ?.superTypes
                ?.any {
                    it.toTypeName() == SCREEN_PARAMS_CLASS
                } == true
        }?.type?.toTypeName() ?: ClassName(instance.packageName.asString(), "${instance.simpleName.asString()}Params")

        // Список параметров для конструктора фабрики
        val factoryConstructorParams = constructorParams
            .filter { it.type.toTypeName() != SCREEN_CONTEXT_CLASS }
            .filter {
                (it.type.resolve().declaration as? KSClassDeclaration)
                    ?.superTypes
                    ?.any {
                        it.toTypeName() == SCREEN_PARAMS_CLASS
                    } != true
            }

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
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(ParameterSpec.builder("context", SCREEN_CONTEXT_CLASS).build())
            .addParameter(ParameterSpec.builder("params", paramsType).build())
            .addCode(returnCodeBlock)
            .returns(instance.toClassName())
            .build()

        TypeSpec.classBuilder(name)
            .addSuperinterface(
                SCREEN_FACTORY_CLASS
                    .parameterizedBy(
                        paramsType,
                        instance.toClassName(),
                    ),
            )
            .primaryConstructorWithPrivateFields(
                factoryConstructorParams.map { it.name!!.getShortName() to it.type.toTypeName() },
            )
            .addModifiers(KModifier.INTERNAL)
            .addFunction(createFunction)
            .build()
            .writeTo(codeGenerator, instance.packageName.asString())
    }

    companion object {
        private val SCREEN_CONTEXT_CLASS = ClassName("ru.vladislavsumin.core.navigation.screen", "ScreenContext")
        private val SCREEN_FACTORY_CLASS = ClassName("ru.vladislavsumin.core.navigation.screen", "ScreenFactory")
        private val SCREEN_PARAMS_CLASS = ClassName("ru.vladislavsumin.core.navigation", "ScreenParams")
    }
}
