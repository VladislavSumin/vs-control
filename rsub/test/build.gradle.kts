plugins {
    kotlin("jvm")
    // id("com.google.devtools.ksp")
}

// sourceSets {
//    main {
//        java {
//            srcDir("build/generated/ksp/main/kotlin")
//        }
//    }
// }
//
// tasks.named<Test>("test") {
//    useJUnitPlatform()
//    this.testLogging {
//        showStandardStreams = true
//    }
// }

dependencies {
    implementation(projects.rsub.client)
    implementation(projects.rsub.server)
//
//    ksp(project(":rsub:ksp:client"))
//    ksp(project(":rsub:ksp:server"))
//
    implementation(projects.core.coroutines)
//    implementation(coreLibs.kotlin.coroutines.core)
//    implementation(coreLibs.vs.core.coroutines)
//    implementation(coreLibs.vs.core.logging)
//    implementation(coreLibs.kotlin.serialization.core)
//    implementation(coreLibs.kotlin.serialization.json)
//
//    testImplementation(coreLibs.kotlin.coroutines.test)
//    testImplementation(coreLibs.testing.turbine)
//
    testImplementation(libs.testing.jupiter.api)
    testRuntimeOnly(libs.testing.jupiter.engine)
    testRuntimeOnly(libs.testing.jupiter.params)
    testRuntimeOnly(libs.testing.jupiter.platformSuite)

    testImplementation(libs.testing.mockito.core)
    testImplementation(libs.testing.mockito.jupiter)
    testImplementation(libs.testing.mockito.kotlin)
}
