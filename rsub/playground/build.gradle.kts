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

dependencies {
    implementation(projects.rsub.connector.ktorWebsocket.client)
    implementation(projects.rsub.connector.ktorWebsocket.server)

    implementation(projects.rsub.client)
    implementation(projects.rsub.server)

//    implementation(coreLibs.vs.core.ktor.server)
//
//    implementation(coreLibs.ktor.server.websocket)
//    implementation(coreLibs.ktor.client.websocket)
//
//    implementation(coreLibs.ktor.client.cio)
//
//    implementation(coreLibs.kotlin.serialization.core)
//    implementation(coreLibs.kotlin.serialization.json)
//    implementation(coreLibs.vs.core.logging)
//
//    ksp(projects.rsub.ksp.client)
//    ksp(projects.rsub.ksp.server)
}
