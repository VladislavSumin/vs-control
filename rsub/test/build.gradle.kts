plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

sourceSets {
    main {
        java {
            srcDir("build/generated/ksp/main/kotlin")
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    this.testLogging {
        showStandardStreams = true
    }
}

dependencies {
    implementation(projects.rsub.client)
    implementation(projects.rsub.server)

    ksp(projects.rsub.ksp.client)
    ksp(projects.rsub.ksp.server)

    implementation(projects.core.coroutines)
    implementation(projects.core.serialization.protobuf)
    implementation(projects.core.logger.api)
    implementation(projects.core.logger.platform)

    testImplementation(projects.core.coroutinesTest)
    testImplementation(libs.testing.turbine)
    testImplementation(libs.testing.jupiter.api)
    testRuntimeOnly(libs.testing.jupiter.engine)
    testRuntimeOnly(libs.testing.jupiter.params)
    testRuntimeOnly(libs.testing.jupiter.platformSuite)

    testImplementation(libs.testing.mockito.core)
    testImplementation(libs.testing.mockito.jupiter)
    testImplementation(libs.testing.mockito.kotlin)
}
