package ru.vs.core.factoryGenerator

/**
 * Генерирует фабрику для основного конструктора класса.
 *
 * Фабрика будет лежать в том же пакете, что и создаваемый ей класс, иметь модификатор internal
 * и называться ClassNameFactory. Внутри фабрики будет один метод create.
 *
 * Все методы аргументы конструктора кроме аргументов помеченных [ByCreate] будут перенесены в конструктор фабрики.
 * Те же аргументы что помечены [ByCreate] будут перенесены в аргументы функции create
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class GenerateFactory
