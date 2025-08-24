package ru.vs.control.entities.factoryGenerator

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
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.vladislavsumin.core.ksp.utils.Types
import ru.vladislavsumin.core.ksp.utils.primaryConstructorWithPrivateFields
import ru.vladislavsumin.core.ksp.utils.processAnnotated
import ru.vladislavsumin.core.ksp.utils.writeTo
import kotlin.reflect.KClass

internal class FactoryGeneratorSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> =
        resolver.processAnnotated<GenerateEntityStateComponentFactory>(::processGenerateFactoryAnnotation)

    private fun processGenerateFactoryAnnotation(instance: KSAnnotated) {
        // Проверяем тип объекта к которому применена аннотация
        if (instance !is KSClassDeclaration) {
            logger.error(
                message = "Is not a class. @GenerateEntityStateComponentFactory applicable only to classes",
                symbol = instance,
            )
            return
        }
        generateFactory(instance)
    }

    /**
     * Generate factory for [instance].
     *
     * @param instance instance for creation
     * @param suffix factory name suffix
     */
    @Suppress("LongMethod", "CyclomaticComplexMethod")
    private fun generateFactory(
        instance: KSClassDeclaration,
        suffix: String = "Factory",
    ) {
        val primaryConstructor = instance.primaryConstructor ?: let {
            logger.error(
                message = "For generate factory class must have primary constructor",
                symbol = instance,
            )
            return
        }

        // Find instance super class reference
        val superClass = instance.superTypes.first()
        if ((superClass.resolve().toTypeName() as? ParameterizedTypeName)?.rawType != BASE_COMPONENT_CLASS) {
            logger.error(
                message = "class must depends on BaseEntityStateComponent",
                symbol = instance,
            )
            return
        }
        val superClassGenericTypeName = superClass.element!!.typeArguments.single().toTypeName()
        val parametrizedFactoryInterface = FACTORY_INTERFACE.parameterizedBy(superClassGenericTypeName)

        val stateType = Types.Coroutines.StateFlow.parameterizedBy(
            ENTITY.parameterizedBy(
                superClassGenericTypeName,
            ),
        )

        val codeBlock = CodeBlock.builder()
            .add("return %T(", instance.toClassName())
            .apply {
                primaryConstructor.parameters.forEach { parameter ->
                    val parameterTypeName = parameter.type.toTypeName()
                    val paramName = when (parameterTypeName) {
                        Types.Coroutines.StateFlow -> "state"
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
            type = KClass::class.asTypeName().parameterizedBy(superClassGenericTypeName),
        )
            .addModifiers(KModifier.OVERRIDE)
            .initializer("%T::class", superClassGenericTypeName)
            .build()

        // Generate "create" function with parameters matches instance primary constructor
        val functionImpl = FunSpec.builder("create")
            .addModifiers(KModifier.OVERRIDE)
            .addCode(codeBlock)
            .addParameter(name = "state", type = stateType)
            .addParameter(name = "context", type = COMPOSE_CONTEXT)
            .returns(instance.toClassName())
            .build()

        val name = instance.simpleName.getShortName() + suffix

        val constructor = primaryConstructor.parameters
            .filter {
                val typeName = it.type.toTypeName()
                typeName != stateType && typeName != COMPOSE_CONTEXT
            }
            .map { it.name!!.getShortName() to it.type.toTypeName() }

        // Generate class impl
        TypeSpec.classBuilder(name)
            .primaryConstructorWithPrivateFields(constructor)
            .addSuperinterface(parametrizedFactoryInterface)
            .addModifiers(KModifier.INTERNAL)
            .addProperty(property)
            .addFunction(functionImpl)
            .addOriginatingKSFile(instance.containingFile!!)
            .build()
            .writeTo(codeGenerator, instance.packageName.asString())
    }

    companion object {
        private val FACTORY_INTERFACE =
            ClassName("ru.vs.control.feature.entities.client.ui.entities.entityState", "EntityStateComponentFactory")
        private val BASE_COMPONENT_CLASS =
            ClassName("ru.vs.control.feature.entities.client.ui.entities.entityState", "BaseEntityStateComponent")
        private val COMPOSE_CONTEXT = ClassName("ru.vs.core.decompose.context", "VsComponentContext")
        private val ENTITY = ClassName("ru.vs.control.feature.entities.client.domain", "Entity")
    }
}
