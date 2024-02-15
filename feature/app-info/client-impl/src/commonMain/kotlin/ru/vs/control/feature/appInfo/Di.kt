package ru.vs.control.feature.appInfo

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.feature.appInfo.domain.AppInfoInteractor
import ru.vs.control.feature.appInfo.domain.AppInfoInteractorImpl
import ru.vs.core.di.Modules

fun Modules.featureAppInfo() = DI.Module("feature-app-info") {
    bindSingleton<AppInfoInteractor> { AppInfoInteractorImpl() }
}
