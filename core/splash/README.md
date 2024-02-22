# Splash screen.

Реализует навигацию для создания splash экранов с возможностью откладывать показ контента.

## Пример использования.

В компоненте необходимо объявить `childSplash` навигацию:

```kotlin
private val splash = childSplash(
    awaitInitialization = {
        // Тут необходимо дождаться инициализацию приложения.
    },
    splashComponentFactory = { context ->
        // Тут необходимо вернуть компонент splash экрана.
    },
    contentComponentFactory = { onContentReady, context ->
        // Тут необходимо вернуть компонент content экрана.
        // Content экран должен дернуть onComponentReady когда будет готов к показу контента.
        // До тех пор, пока onContentReady не будет дернут, будет отображаться Splash экран.
    },
)
```

Далее в контенте можно отрисовать навигацию:

```kotlin
Children(splash, modifier) { // it->
    // тут it будет либо контентом, либо splash экраном, необходимо отрисовать этот компонент
}
```
