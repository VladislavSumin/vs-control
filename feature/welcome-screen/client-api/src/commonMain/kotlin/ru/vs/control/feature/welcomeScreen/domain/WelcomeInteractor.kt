package ru.vs.control.feature.welcomeScreen.domain

interface WelcomeInteractor {
    /**
     * Нужно ли показывать welcome экран.
     */
    suspend fun isNeedToShowWelcomeScreen(): Boolean
}
