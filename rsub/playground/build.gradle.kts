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

dependencies {
    implementation(projects.rsub.connector.ktorWebsocket.client)
    implementation(projects.rsub.connector.ktorWebsocket.server)

    implementation(projects.rsub.client)
    implementation(projects.rsub.server)

    implementation(projects.core.ktor.server)
    implementation(projects.core.ktor.client)

    implementation(projects.core.serialization.protobuf)
    implementation(projects.core.logger.api)
    implementation(projects.core.logger.platform)

    implementation(libs.ktor.server.cio)

    ksp(projects.rsub.ksp.client)
    ksp(projects.rsub.ksp.server)
}
