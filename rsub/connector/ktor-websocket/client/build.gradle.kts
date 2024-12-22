plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.rsub.connector.ktorWebsocket.core)

            implementation(projects.rsub.client)
            implementation(projects.core.ktor.client)
        }
    }
}
