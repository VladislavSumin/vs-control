package ru.vs.convention.kmp

/**
 * Подключает все поддерживаемые таргеты kotlin kmp кроме android, так как android таргет требует явное указание
 * является ли модуль библиотекой или приложением.
 */

plugins {
    // Из-за старого котлина нет CIO для js
    // id("ru.vladislavsumin.convention.kmp.js")
    id("ru.vladislavsumin.convention.kmp.jvm")
    id("ru.vladislavsumin.convention.kmp.ios")
    id("ru.vladislavsumin.convention.kmp.macos")

    // TODO включить обратно
    // не поддерживается sqldelight
    // id("ru.vs.convention.kmp.wasm")
}
