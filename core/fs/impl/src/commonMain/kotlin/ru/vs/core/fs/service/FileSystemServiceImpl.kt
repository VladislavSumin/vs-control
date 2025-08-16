package ru.vs.core.fs.service

import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

internal class FileSystemServiceImpl(
    fileSystemBaseDirProvider: FileSystemBaseDirProvider,
) : FileSystemService {
    private val fs = SystemFileSystem
    private val baseDir = fs.resolve(fileSystemBaseDirProvider.getBaseDir())

    /**
     * Папка для хранения пропертей.
     */
    private val preferencesDir by lazy {
        val path = Path(baseDir, "preferences")
        fs.createDirectories(path)
        fs.resolve(path)
    }

    private val databaseDir by lazy {
        val path = Path(baseDir, "databases")
        fs.createDirectories(path)
        fs.resolve(path)
    }

    override fun getPreferencesPath(name: String): Path {
        return Path(preferencesDir, name)
    }

    override fun getDatabaseDirPath(): Path = databaseDir
}
