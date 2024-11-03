package ru.vs.core.navigation.factoryGenerator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
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
        return resolver.getSymbolsWithAnnotation(GenerateScreenFactory::class.qualifiedName!!)
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
     * Создает фабрику для создания экземпляров [instance] с одним методом create.
     *
     * @param instance инстанс который должна создавать фабрика
     */
    @Suppress("LongMethod")
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
            .addParameter(
                ParameterSpec.builder(
                    "context",
                    SCREEN_CONTEXT_CLASS,
                ).build(),
            )
            .addParameter(
                ParameterSpec.builder(
                    "params",
                    paramsType,
                ).build(),
            )
            .addCode(returnCodeBlock)
            .returns(instance.toClassName())
            .build()

        // Конструктор фабрики, обратите внимание, нельзя объявить сразу конструктор с пропертями + полями класса
        // поля класса отдельно объявляются ниже.
        val constructor = FunSpec.constructorBuilder()
            .apply {
                factoryConstructorParams.forEach {
                    addParameter(it.name!!.getShortName(), it.type.toTypeName())
                }
            }
            .build()

        // Generate class impl
        val clazzImpl = TypeSpec.classBuilder(name)
            .addSuperinterface(
                SCREEN_FACTORY_CLASS
                    .parameterizedBy(
                        paramsType,
                        instance.toClassName(),
                    ),
            )
            .primaryConstructor(constructor)
            .addModifiers(KModifier.INTERNAL)
            .apply {
                factoryConstructorParams.forEach {
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

    companion object {
        private val SCREEN_CONTEXT_CLASS = ClassName("ru.vs.core.navigation.screen", "ScreenContext")
        private val SCREEN_FACTORY_CLASS = ClassName("ru.vs.core.navigation.screen", "ScreenFactory")
        private val SCREEN_PARAMS_CLASS = ClassName("ru.vs.core.navigation", "ScreenParams")
    }
}
