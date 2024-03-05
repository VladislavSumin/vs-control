# Кастомные правила Detekt.

Все кастомные правила можно посмотреть [тут](src/main/kotlin/ru/vs/customDetektRules/rules)

## Обновление правил.

При изменении кастомных правил detekt подхватывает их не сразу, что бы подхватить новые правила нужно сначала
сделать `./gradlew --stop` что бы прибить gradle демон, а потом вызвать `gradle clean detekt`.

Для подключения дебагера нужно сделать то же самое что и в пункте выше + запустить все это с дебагом, студия подхватит
точки останова.

## Создание новых правил

1. Создать новое правило используя шаблон `DetektRule`
2. Зарегистрировать правило
   в [CustomDetektRuleSetProvider](src/main/kotlin/ru/vs/customDetektRules/CustomDetektRuleSetProvider.kt).
3. Включить правило в [config.yml](src/main/resources/config/config.yml).
4. Написать правило.