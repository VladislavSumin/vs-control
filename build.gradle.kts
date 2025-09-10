import ru.vladislavsumin.utils.registerExternalModuleDetektTask

plugins {
    id("ru.vladislavsumin.convention.analyze.detekt-all")
    id("ru.vs.convention.analyze.detekt-custom-rules-all")
    id("ru.vs.convention.analyze.check-module-graph")
}

registerExternalModuleDetektTask("detektBuildLogic", projectDir.resolve("build-logic"))
