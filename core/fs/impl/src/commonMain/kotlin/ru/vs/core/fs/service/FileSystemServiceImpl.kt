package ru.vs.core.fs.service

import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

internal class FileSystemServiceImpl : FileSystemService {
    private val fs = SystemFileSystem
    private val currentDir = fs.resolve(Path("."))

    /**
     * Папка для хранения пропертей.
     */
    private val preferencesDir by lazy {
        val path = Path(currentDir, "preferences")
        fs.createDirectories(path)
        fs.resolve(path)
    }

    override fun getPreferencesPath(name: String): Path {
        return Path(preferencesDir, name)
    }
}
