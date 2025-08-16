package ru.vs.core.fs.service

import kotlinx.io.files.Path

internal actual fun org.kodein.di.DirectDI.createFileSystemBaseDirProvider(): FileSystemBaseDirProvider {
    return object : FileSystemBaseDirProvider {
        override fun getBaseDir(): Path = Path(".")
    }
}
