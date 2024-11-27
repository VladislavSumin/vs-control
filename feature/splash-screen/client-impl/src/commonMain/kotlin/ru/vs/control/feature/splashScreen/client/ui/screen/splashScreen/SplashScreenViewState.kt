package ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen

internal sealed interface SplashScreenViewState {
    /**
     * Начало загрузки, показываем только лого, в надежде на быструю загрузку.
     */
    data object FastLoading : SplashScreenViewState

    /**
     * Загрузка затянулась, добавляем к лого индикатор загрузки.
     */
    data object LongLoading : SplashScreenViewState
}
