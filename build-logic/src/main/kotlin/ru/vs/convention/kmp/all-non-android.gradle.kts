package ru.vs.convention.kmp

/**
 * Подключает все поддерживаемые таргеты kotlin kmp кроме android, так как android таргет требует явное указание
 * является ли модуль библиотекой или приложением.
 */

plugins {
    // TODO включить обратно
    // не поддерживается ktor cio но поддержка уже вот вот так что скоро можно будет включать обратно
    // id("ru.vs.convention.kmp.js")
    id("ru.vs.convention.kmp.jvm")

    // TODO включить обратно
    // не поддерживается ktor cio
    // id("ru.vs.convention.kmp.macos")

    // TODO включить обратно
    // не поддерживается sqldelight
    // id("ru.vs.convention.kmp.wasm")
}
