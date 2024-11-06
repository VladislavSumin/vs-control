plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
    id("app.cash.sqldelight")
}

sqldelight {
    databases {
        register("Database") {
            packageName.set("ru.vs.control.feature.embeddedServer.repository")
        }
    }
}

kotlin {
    sourceSets {
        val ktorMain by creating {
            dependencies {
                implementation(projects.server.embedded)
            }
            dependsOn(commonMain.get())
        }

        val nonKtorMain by creating {
            dependsOn(commonMain.get())
        }

        jvmMain.get().dependsOn(ktorMain)
        androidMain.get().dependsOn(ktorMain)

        jsMain.get().dependsOn(nonKtorMain)
        wasmJsMain.get().dependsOn(nonKtorMain)
        nativeMain.get().dependsOn(nonKtorMain)

        commonMain.dependencies {
            implementation(projects.feature.rootContentScreen.clientApi)
        }
    }
}
