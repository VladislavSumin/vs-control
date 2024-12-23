package ru.vs.rsub.ksp.client

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KType

class RSubInterfaceProxyGenerator(
    private val logger: KSPLogger,
) {

    fun TypeSpec.Builder.generateProxyClassesWithProxyInstances(
        supperInterface: KSClassDeclaration,
    ): TypeSpec.Builder {
        supperInterface.getAllProperties().forEach { generateProxyClassWithProxyInstance(it) }
        return this
    }

    private fun TypeSpec.Builder.generateProxyClassWithProxyInstance(
        superProperty: KSPropertyDeclaration,
    ) {
        // Check interface property return type it must be another interface market with RSubInterface annotation
        val superInterface = superProperty.type.resolve().declaration as KSClassDeclaration

        // Adds property that's implements given superInterface
        generateProxyHolder(superProperty, superInterface)

        generateProxyClass(superInterface)
    }

    /**
     * Generate properties override holder with [generateProxyClass] impl
     * Output example:
     * ```
     *  public override val entities: EntitiesRsub = EntitiesRsubImpl()
     * ```
     */
    private fun TypeSpec.Builder.generateProxyHolder(
        superProperty: KSPropertyDeclaration,
        superInterface: KSClassDeclaration,
    ) = addProperty(
        PropertySpec.builder(
            superProperty.simpleName.asString(),
            superInterface.toClassName(),
            KModifier.OVERRIDE,
        )
            .initializer("${superInterface.simpleName.asString()}Impl()")
            .build(),
    )

    /**
     * Generate proxy implementation for given [superInterface]
     * Output example:
     * ```
     *   private inner class EntitiesRsubImpl : EntitiesRsub {
     *     public override fun observeEntities(): Flow<List<EntityDto>> = processFlow("EntitiesRsub", "observeEntities")
     *   }
     * ```
     */
    private fun TypeSpec.Builder.generateProxyClass(superInterface: KSClassDeclaration) = addType(
        TypeSpec.classBuilder("${superInterface.simpleName.asString()}Impl")
            .addModifiers(KModifier.PRIVATE, KModifier.INNER)
            .addSuperinterface(superInterface.toClassName())
            .generateProxyFunctions(superInterface)
            .build(),
    )

    /**
     * Generates function implementation for function at given [baseInterface]
     * @see generateProxyFunction
     */
    private fun TypeSpec.Builder.generateProxyFunctions(baseInterface: KSClassDeclaration): TypeSpec.Builder {
        val interfaceName = baseInterface.simpleName.asString()
        baseInterface.getAllFunctions()
            .filter { it.isAbstract }
            .forEach { generateProxyFunction(interfaceName, it) }
        return this
    }

    /**
     * Generate function implementation
     * @see generateSuspendProxyFunction
     * @see generateProxyFunction
     */
    private fun TypeSpec.Builder.generateProxyFunction(
        interfaceName: String,
        function: KSFunctionDeclaration,
    ): TypeSpec.Builder = when {
        function.modifiers.contains(Modifier.SUSPEND) -> {
            generateSuspendProxyFunction(interfaceName, function)
        }

        (function.returnType!!.resolve().toTypeName() as? ParameterizedTypeName)
            ?.rawType == Flow::class.asClassName() -> {
            generateFlowProxyFunction(interfaceName, function)
        }

        else -> {
            logger.error("Cannot generate wrapper for this function", function)
            error("Cannot generate wrapper for this function")
        }
    }

    private fun TypeSpec.Builder.generateSuspendProxyFunction(
        interfaceName: String,
        function: KSFunctionDeclaration,
    ): TypeSpec.Builder = addFunction(
        FunSpec.builder(function.simpleName.asString())
            .addModifiers(KModifier.OVERRIDE, KModifier.SUSPEND)
            .addParameters(function.parameters.map { ParameterSpec(it.name!!.asString(), it.type.toTypeName()) })
            .returns(function.returnType!!.toTypeName())
            .addArgumentsStatement(function)
            .addCode(
                "return processSuspend(%S, %S, %N, %N)",
                interfaceName,
                function.simpleName.asString(),
                ARGUMENTS_TYPES_NAME,
                ARGUMENTS_NAME,
            )
            .build(),
    )

    private fun TypeSpec.Builder.generateFlowProxyFunction(
        interfaceName: String,
        function: KSFunctionDeclaration,
    ) = addFunction(
        FunSpec.builder(function.simpleName.asString())
            .addModifiers(KModifier.OVERRIDE)
            .addParameters(function.parameters.map { ParameterSpec(it.name!!.asString(), it.type.toTypeName()) })
            .returns(function.returnType!!.toTypeName())
            .addArgumentsStatement(function)
            .addCode(
                "return processFlow(%S, %S, %N, %N)",
                interfaceName,
                function.simpleName.asString(),
                ARGUMENTS_TYPES_NAME,
                ARGUMENTS_NAME,
            )
            .build(),
    )

    private fun FunSpec.Builder.addArgumentsStatement(function: KSFunctionDeclaration): FunSpec.Builder {
        val paramsTypes = function.parameters.map { it.type.toTypeName() }
        val params = function.parameters.joinToString { it.name!!.asString() }
        addCode(
            CodeBlock.builder()
                .addStatement("val $ARGUMENTS_TYPES_NAME = listOf<%T>(", KType::class.asClassName())
                .apply {
                    paramsTypes.forEach { addStatement("%M<%T>(),", typeOfMember, it) }
                }
                .addStatement(")")
                .build(),
        )
        addStatement("val $ARGUMENTS_NAME = listOf<Any>($params)")
        return this
    }

    companion object {
        private val typeOfMember = MemberName("kotlin.reflect", "typeOf")
        private const val ARGUMENTS_NAME = "arguments"
        private const val ARGUMENTS_TYPES_NAME = "argumentsTypes"
    }
}
