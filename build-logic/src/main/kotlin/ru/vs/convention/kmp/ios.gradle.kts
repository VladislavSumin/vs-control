package ru.vs.convention.kmp

/**
 * Базовая настройка iOS таргета для KMP.
 */

plugins {
    id("ru.vladislavsumin.convention.kmp.common")
}

kotlin {
    iosX64()
    iosArm64()
}
