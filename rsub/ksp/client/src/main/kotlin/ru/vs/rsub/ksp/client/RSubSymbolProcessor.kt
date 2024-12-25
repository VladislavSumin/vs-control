package ru.vs.rsub.ksp.client

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import ru.vs.core.ksp.Types
import ru.vs.core.ksp.processAnnotated
import ru.vs.core.ksp.writeTo
import ru.vs.rsub.RSubClient
import ru.vs.rsub.RSubClientAbstract
import ru.vs.rsub.RSubConnector

class RSubSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    logger: KSPLogger,
) : SymbolProcessor {
    private val proxyGenerator = RSubInterfaceProxyGenerator(logger)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        return resolver.processAnnotated<RSubClient> {
            check(it is KSClassDeclaration) { "Only KSClassDeclaration supported, but $it was received" }
            generateRSubClientImpl(it)
        }
    }

    /**
     * Generating new class with name client.name + Impl that's provide all client functionality
     */
    private fun generateRSubClientImpl(client: KSClassDeclaration) {
        val name = client.simpleName.asString() + "Impl"
        val constructor = generateConstructor()
        with(proxyGenerator) {
            TypeSpec.classBuilder(name)
                .addOriginatingKSFile(client.containingFile!!)
                .addModifiers(KModifier.INTERNAL)
                .superclass(RSubClientAbstract::class)
                .addSuperinterface(client.toClassName())
                .primaryConstructor(constructor)
                .addSuperclassConstructorParameter(constructor.parameters.joinToString { it.name })
                .generateProxyClassesWithProxyInstances(client)
                .build()
        }.writeTo(codeGenerator, client.packageName.asString())
    }

    @Suppress("MagicNumber")
    private fun generateConstructor(): FunSpec {
        return FunSpec.constructorBuilder()
            .addParameter("connector", RSubConnector::class)
            .addParameter(
                ParameterSpec.builder("reconnectInterval", Long::class)
                    .defaultValue("%L", 3000)
                    .build(),
            )
            .addParameter(
                ParameterSpec.builder("connectionKeepAliveTime", Long::class)
                    .defaultValue("%L", 6000)
                    .build(),
            )
            .addParameter(
                ParameterSpec.builder("protobuf", Types.Serialization.ProtoBuf)
                    .defaultValue("%T", Types.Serialization.ProtoBuf)
                    .build(),
            )
            .addParameter(
                ParameterSpec.builder("scope", Types.Coroutines.CoroutineScope)
                    .defaultValue("%T", Types.Coroutines.GlobalScope)
                    .build(),
            )
            .build()
    }
}
