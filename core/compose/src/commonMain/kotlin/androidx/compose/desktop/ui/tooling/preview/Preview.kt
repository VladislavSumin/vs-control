package androidx.compose.desktop.ui.tooling.preview

/**
 * Замена стандартной аннотации preview, для KMM кода.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.FUNCTION,
)
annotation class Preview
