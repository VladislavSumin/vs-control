package ru.vs.convention.kmp

/**
 * Подключает все поддерживаемые таргеты kotlin kmp разом.
 * Android таргет в данном случае подключается как android библиотека.
 */

plugins {
    id("ru.vs.convention.kmp.android-library")
    id("ru.vs.convention.kmp.all-non-android")
}
