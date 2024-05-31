package ru.vs.control.feature.welcomeScreen.domain

interface WelcomeInteractor {
    /**
     * Нужно ли показывать welcome экран.
     */
    val isNeedToShowWelcomeScreen: Boolean
}
