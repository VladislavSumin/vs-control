package ru.vs.control.entities.factory_generator

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
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import kotlin.reflect.KClass

// TODO переписать с базовыми расширениями
internal class FactoryGeneratorSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        return resolver.getSymbolsWithAnnotation(GenerateEntityStateComponentFactory::class.qualifiedName!!)
            .filterNot(this::processAnnotated)
            .toList()
    }

    @Suppress("TooGenericExceptionCaught")
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

        generateFactoryInterfaceAndImpl(instance)
    }

    /**
     * Generate factory for [instance].
     *
     * @param instance instance for creation
     * @param suffix factory name suffix
     */
    private fun generateFactoryInterfaceAndImpl(
        instance: KSClassDeclaration,
        suffix: String = "Factory",
    ) {
        // Find instance super class reference
        val superClass = instance.superTypes.first()
        check(superClass.resolve().toClassName() == BASE_COMPONENT_CLASS) {
            "class must depends on BaseEntityStateComponent"
        }
        val superClassGenericTypeName = superClass.element!!.typeArguments.single().toTypeName()
        val parametrizedFactoryInterface = FACTORY_INTERFACE.parameterizedBy(superClassGenericTypeName)

        val stateType = STATE_FLOW.parameterizedBy(
            ENTITY.parameterizedBy(
                superClassGenericTypeName
            )
        )

        val codeBlock = CodeBlock.builder()
            .add("return %T(", instance.toClassName())
            .apply {
                instance.primaryConstructor!!.parameters.forEach { parameter ->
                    val parameterTypeName = parameter.type.toTypeName()
                    val paramName = when (parameterTypeName) {
                        STATE_FLOW -> "state"
                        COMPOSE_CONTEXT -> "context"
                        else -> parameter.name!!.getShortName()
                    }
                    add("%L, ", paramName)
                }
            }
            .add(")")
            .build()

        val property = PropertySpec.builder(
            name = "entityStateType",
            type = KClass::class.asTypeName().parameterizedBy(superClassGenericTypeName)
        )
            .addModifiers(KModifier.OVERRIDE)
            .initializer("%T::class", superClassGenericTypeName)
            .build()

        // Generate "create" function with parameters matches instance primary constructor
        val functionImpl = FunSpec.builder("create")
            .addModifiers(KModifier.OVERRIDE)
            .addCode(codeBlock)
            .addParameter(
                ParameterSpec.builder(
                    name = "state",
                    type = stateType
                ).build()
            )
            .addParameter(
                ParameterSpec.builder(
                    name = "context",
                    type = COMPOSE_CONTEXT
                ).build()
            )
            .returns(instance.toClassName())
            .build()

        // Generate constructor
        val constructor = FunSpec.constructorBuilder()
            .apply {
                instance.primaryConstructor!!.parameters
                    .filter {
                        val typeName = it.type.toTypeName()
                        typeName != stateType && typeName != COMPOSE_CONTEXT
                    }
                    .forEach {
                        addParameter(it.name!!.getShortName(), it.type.toTypeName())
                    }
            }
            .build()

        val name = instance.simpleName.getShortName() + suffix

        // Generate class impl
        val clazzImpl = TypeSpec.classBuilder(name)
            .primaryConstructor(constructor)
            .addSuperinterface(parametrizedFactoryInterface)
            .addModifiers(KModifier.INTERNAL)
            .apply {
                instance.primaryConstructor!!.parameters
                    .filter {
                        val typeName = it.type.toTypeName()
                        typeName != stateType && typeName != COMPOSE_CONTEXT
                    }
                    .forEach {
                        val name = it.name!!.getShortName()
                        addProperty(
                            PropertySpec.builder(name, it.type.toTypeName())
                                .initializer(name)
                                .addModifiers(KModifier.PRIVATE)
                                .build()
                        )
                    }
            }
            .addProperty(property)
            .addFunction(functionImpl)
            .build()

        // Generate kotlin file
        FileSpec.builder(
            instance.packageName.asString(),
            name,
        )
            .addType(clazzImpl)
            .build()
            .writeTo(codeGenerator, false)
    }

    companion object {
        private val FACTORY_INTERFACE =
            ClassName("ru.vs.control.entities.ui.entities.entity_state", "EntityStateComponentFactory")
        private val BASE_COMPONENT_CLASS =
            ClassName("ru.vs.control.entities.ui.entities.entity_state", "BaseEntityStateComponent")
        private val COMPOSE_CONTEXT = ClassName("com.arkivanov.decompose", "ComponentContext")
        private val STATE_FLOW = ClassName("kotlinx.coroutines.flow", "StateFlow")
        private val ENTITY = ClassName("ru.vs.control.entities.domain", "Entity")
    }
}
