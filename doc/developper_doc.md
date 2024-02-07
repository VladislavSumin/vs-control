# Документация для разработчиков.

* [Convention плагины](developper/convention_plugins.md)
* [Кастомные github actions](developper/github_actions.md)
* [Список костылей](developper/bad_decisions.md)

## Core библиотеки

* [compose](../core/compose/README.md) базовый код compose.
* [logger](../core/logger/README.md) общий логгер для всех модулей.

## [Требования к дизайну](design/design_rules.md)

## Build properties

Все property, используемые при сборке доступны
через [общую точку входа](../build-logic/src/main/kotlin/ru/vs/configuration/ProjectConfiguration.kt), там можно
посмотреть документацию по каждой property.