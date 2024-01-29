# Документация по gradle convention плагинам.

Все convention плагины находятся в `build-logic` в пакете `ru.vs.convention`

## `ru.vs.convention.kmp.*`

В данном пакете находятся плагины связанные с настройкой kotlin multiplatform.

* [common-tests](../../build-logic/src/main/kotlin/ru/vs/convention/kmp/common-tests.gradle.kts) содержит базовую
  настройку для поддержки unit тестов.
* [common](../../build-logic/src/main/kotlin/ru/vs/convention/kmp/common.gradle.kts) содержит базовую настройку kmp для
  всех платформ.
* [jvm](../../build-logic/src/main/kotlin/ru/vs/convention/kmp/jvm.gradle.kts) содержит базовую настройку для jvm
  таргета kmp.

## `ru.vs.convention.android.*`

В данном пакете находятся плагины для настройки android плагина.

* [base](../../build-logic/src/main/kotlin/ru/vs/convention/android/base.gradle.kts) базовая настройка android плагина
  без привязки к конкретной имплементации (application/library/итд).
* [base-kotlin](../../build-logic/src/main/kotlin/ru/vs/convention/android/base-kotlin.gradle.kts) расширение базовой
  android настройки, включает в себя настройку
  kotlin.
* [application](../../build-logic/src/main/kotlin/ru/vs/convention/android/application.gradle.kts) Базовая настройка
  android application.

## `ru.vs.convention.analyze.*`

В данном пакете находятся плагины для настройки анализаторов кода.

* [detekt](../../build-logic/src/main/kotlin/ru/vs/convention/analyze/detekt.gradle.kts) содержит базовую настройку
  плагина detekt.
* [detekt-build-logic](../../build-logic/src/main/kotlin/ru/vs/convention/analyze/detekt-build-logic.gradle.kts)
  содержит дополнительную настройку detekt для анализа build-logic модуля.
