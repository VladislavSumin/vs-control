package ru.vs.core.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.squareup.kotlinpoet.FileSpec
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
