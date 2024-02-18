# Документация по gradle convention плагинам.

Все convention плагины находятся в `build-logic` в пакете `ru.vs.convention`

* [ru.vs.convention.compose](../../build-logic/src/main/kotlin/ru/vs/convention/compose.gradle.kts) настройки по
  умолчанию для jetbrains compose плагина.
* [ru.vs.convention.impl-to-api-dependency](../../build-logic/src/main/kotlin/ru/vs/convention/impl-to-api-dependency.gradle.kts)
  автоматически прописывает *-impl модулю api зависимость на *-api модуль.
* [ru.vs.convention.atomicfu](../../build-logic/src/main/kotlin/ru/vs/convention/atomicfu.gradle.kts) настраивает
  atomicfu.

## [ru.vs.convention.kmp.*](../../build-logic/src/main/kotlin/ru/vs/convention/preset)

В данном пакете находятся настройки по умолчанию (пресеты) для различных типовых модулей.

## [ru.vs.convention.kmp.*](../../build-logic/src/main/kotlin/ru/vs/convention/kmp)

В данном пакете находятся плагины связанные с настройкой kotlin multiplatform.

## [ru.vs.convention.android.*](../../build-logic/src/main/kotlin/ru/vs/convention/android)

В данном пакете находятся плагины для настройки android плагина.

## [ru.vs.convention.analyze.*](../../build-logic/src/main/kotlin/ru/vs/convention/analyze)

В данном пакете находятся плагины для настройки анализаторов кода.
