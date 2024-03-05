# Документация для разработчиков.

* [Структура модулей и пакетов](developper/module_and_package_structure.md)
* [Core модули](../core/README.md)
* [Feature модули](../feature/README.md)
* [Convention плагины](developper/convention_plugins.md)
* [Шаблоны для генерации кода](developper/templates.md)
* [Кастомные github actions](developper/github_actions.md)
* [Кастомные правила detekt](../custom-detekt-rules/README.md)
* [Список костылей](developper/bad_decisions.md)
* [Client](../client/README.md)

## [Требования к дизайну](design/design_rules.md)

## Build properties

Все property, используемые при сборке доступны
через [общую точку входа](../build-logic/src/main/kotlin/ru/vs/configuration/ProjectConfiguration.kt), там можно
посмотреть документацию по каждой property.