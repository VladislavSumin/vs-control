package ru.vs.control.feature.welcomeScreen.domain

internal interface WelcomeInteractorInternal : WelcomeInteractor {
    /**
     * Должна вызываться после прохождения welcome экрана.
     */
    suspend fun passWelcomeScreen()
}

internal class WelcomeInteractorImpl : WelcomeInteractorInternal {
    override val isNeedToShowWelcomeScreen: Boolean = true

    override suspend fun passWelcomeScreen() {
        // no acton
    }
}
