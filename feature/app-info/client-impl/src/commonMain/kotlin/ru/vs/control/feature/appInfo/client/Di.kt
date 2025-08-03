package ru.vs.control.feature.appInfo.client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vs.control.feature.appInfo.client.domain.AppInfoInteractor
import ru.vs.control.feature.appInfo.client.domain.AppInfoInteractorImpl

fun Modules.featureAppInfo() = DI.Module("feature-app-info") {
    bindSingleton<AppInfoInteractor> { AppInfoInteractorImpl() }
}
