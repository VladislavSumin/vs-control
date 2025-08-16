package ru.vs.core.fs

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vs.core.fs.service.FileSystemService
import ru.vs.core.fs.service.FileSystemServiceImpl
import ru.vs.core.fs.service.createFileSystemBaseDirProvider

fun Modules.coreFs() = DI.Module("core-fs") {
    bindSingleton<FileSystemService> {
        val provider = createFileSystemBaseDirProvider()
        FileSystemServiceImpl(provider)
    }
}
