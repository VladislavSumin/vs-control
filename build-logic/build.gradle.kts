plugins {
    `kotlin-dsl`
}

dependencies {
    // Мы хотим получать доступ к libs из наших convention плагинов, но гредл на текущий момент не умеет прокидывать
    // version catalogs. Поэтому используем костыль отсюда - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation("ru.vladislavsumin:build-scripts")

    implementation(projects.core.database.plugin)

    implementation(vsCoreLibs.gradlePlugins.kotlin.core)
    implementation(vsCoreLibs.gradlePlugins.kotlin.ksp)
    implementation(vsCoreLibs.gradlePlugins.kotlin.compose.compiler)
    implementation(vsCoreLibs.gradlePlugins.kotlin.serialization)
    implementation(libs.gradlePlugins.kotlin.atomicfu)
    implementation(libs.gradlePlugins.jb.compose)
    implementation(libs.gradlePlugins.sqldelight)
    implementation(vsCoreLibs.gradlePlugins.android)
    implementation(libs.gradlePlugins.detekt)
    implementation(libs.gradlePlugins.modulesGraphAssert)
}
