package ru.vs.core.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * Записывает [TypeSpec] в файл создавая [FileSpec] по умолчанию.
 * Удобно когда в файл нужно записать только один тип.
 */
fun TypeSpec.writeTo(
    codeGenerator: CodeGenerator,
    packageName: String,
    aggregating: Boolean = false,
) {
    FileSpec.builder(
        packageName,
        name!!,
    )
        .addType(this)
        .build()
        .writeTo(codeGenerator, aggregating)
}

/**
 * Добавляет просто конструктор вида:
 * constructor(private val value: ValueType)
 */
fun TypeSpec.Builder.primaryConstructorWithPrivateFields(
    params: Iterable<Pair<String, TypeName>>,
): TypeSpec.Builder {
    val constructor = FunSpec.constructorBuilder()
        .apply {
            params.forEach {
                addParameter(it.first, it.second)
            }
        }
        .build()
    primaryConstructor(constructor)

    params.forEach {
        addProperty(
            PropertySpec.builder(it.first, it.second)
                .initializer(it.first)
                .addModifiers(KModifier.PRIVATE)
                .build(),
        )
    }
    return this
}
