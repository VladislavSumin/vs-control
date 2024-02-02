plugins {
    id("ru.vs.convention.kmp.macos")
}

kotlin {
    macosX64 {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    macosArm64 {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    sourceSets {
        macosMain {
            dependencies {
                implementation(projects.client.common)
            }
        }
    }
}
