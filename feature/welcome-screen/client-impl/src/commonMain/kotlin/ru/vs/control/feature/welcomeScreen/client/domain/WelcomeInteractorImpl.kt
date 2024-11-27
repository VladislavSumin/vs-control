package ru.vs.control.feature.welcomeScreen.client.domain

import kotlinx.coroutines.flow.first
import ru.vs.core.properties.Properties
import ru.vs.core.properties.PropertiesKey
import ru.vs.core.properties.PropertiesService
import ru.vs.core.properties.Property
import ru.vs.core.properties.PropertyKey

internal interface WelcomeInteractorInternal : WelcomeInteractor {
    /**
     * Должна вызываться после прохождения welcome экрана.
     */
    suspend fun passWelcomeScreen()
}

internal class WelcomeInteractorImpl(
    propertiesService: PropertiesService,
) : WelcomeInteractorInternal {
    private val properties: Properties = propertiesService.getProperties(PropertiesKey("welcome_screen"))
    private val isPassedOnce: Property<Boolean> = properties.getProperty(PropertyKey("is_passed_once"), false)

    override suspend fun isNeedToShowWelcomeScreen(): Boolean = !isPassedOnce.first()

    override suspend fun passWelcomeScreen() {
        isPassedOnce.set(true)
    }
}
