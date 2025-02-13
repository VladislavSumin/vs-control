includeBuild("../../vs-core/build-scripts")

apply { from("common-settings.gradle.kts") }
rootProject.name = "build-logic"

include(":core:database:plugin")
project(":core:database:plugin").projectDir = File("../core/database/plugin")
