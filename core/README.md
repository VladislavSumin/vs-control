# Документация по Core модулям.

Core модули могут содержать только самый базовый функционал, такой функционал не должен быть привязан к конкретному
проекту и может быть легко использован в другом проекте.

В core модулях разрешены **только** зависимости на другие core модули и сторонние библиотеки.

## Список core модулей.

* [compose](compose/README.md) базовый код compose.
* [decompose](decompose/README.md) базовый код decompose.
* [di](di/README.md) базовый код di.
* [logger](logger/README.md) общий логгер для всех модулей.
* [navigation](navigation/README.md) фреймворк навигации.
* [properties](properties/README.md) фреймворк для работы с сохранением пар ключ-значение.
* [serialization](serialization/README.md) фреймворк для работы с сериализацией данных.
* [splash](splash/README.md) навигация для splash экрана.
* [uikit](uikit/README.md) дизайн система.