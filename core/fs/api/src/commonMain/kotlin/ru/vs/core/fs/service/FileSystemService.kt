package ru.vs.core.fs.service

import kotlinx.io.files.Path

/**
 * Основной интерфейс для работы с файловой системой, любая работа с файлами должна происходить только через него
 */
interface FileSystemService {
    /**
     * Возвращает путь к пропертям.
     * @param name имя + расширение.
     */
    fun getPreferencesPath(name: String): Path

    /**
     * Возвращает путь к папке с базами данных.
     */
    fun getDatabaseDirPath(): Path
}
