package ru.vs.control.feature.rootContentScreen.client.ui.screen.rootContentScreen

import ru.vladislavsumin.core.navigation.NavigationHost

/**
 * Навигационный хост главного экрана приложения, реализует навигацию по типа stack.
 * Экраны в стеке открываются поверх главного экрана (поверх табов, меню и прочего).
 * Таким образом экраны получаются полноэкранными и должны сами обрабатывать все инсеты.
 */
object RootContentNavigationHost : NavigationHost
