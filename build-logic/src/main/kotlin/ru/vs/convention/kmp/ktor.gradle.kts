package ru.vs.convention.kmp

/**
 * Подключает все таргеты kmp которые поддерживает ktor.
 * Android таргет в данном случае подключается как android библиотека.
 */

plugins {
    id("ru.vs.convention.kmp.android-library")
    id("ru.vs.convention.kmp.jvm")
}
