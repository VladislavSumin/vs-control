plugins {
    id("ru.vs.convention.preset.feature-client-impl")
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
    }
}
