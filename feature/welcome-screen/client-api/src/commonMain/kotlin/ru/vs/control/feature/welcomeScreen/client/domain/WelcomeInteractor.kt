package ru.vs.control.feature.welcomeScreen.client.domain

interface WelcomeInteractor {
    /**
     * Нужно ли показывать welcome экран.
     */
    suspend fun isNeedToShowWelcomeScreen(): Boolean
}
