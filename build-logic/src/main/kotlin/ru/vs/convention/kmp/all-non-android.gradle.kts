package ru.vs.convention.kmp

/**
 * Подключает все поддерживаемые таргеты kotlin kmp кроме android, так как android таргет требует явное указание
 * является ли модуль библиотекой или приложением.
 */

plugins {
    id("ru.vs.convention.kmp.js")
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.macos")

    // TODO включить обратно
    // не поддерживается sqldelight
    // id("ru.vs.convention.kmp.wasm")
}
