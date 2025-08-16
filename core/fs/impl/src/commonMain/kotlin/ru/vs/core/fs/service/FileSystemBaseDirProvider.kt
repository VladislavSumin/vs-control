package ru.vs.core.fs.service

import kotlinx.io.files.Path
import org.kodein.di.DirectDI

internal interface FileSystemBaseDirProvider {
    fun getBaseDir(): Path
}

internal expect fun DirectDI.createFileSystemBaseDirProvider(): FileSystemBaseDirProvider
