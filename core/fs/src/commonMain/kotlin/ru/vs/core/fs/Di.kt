package ru.vs.core.fs

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.fs.service.FileSystemService
import ru.vs.core.fs.service.FileSystemServiceImpl

fun Modules.coreFs() = DI.Module("core-fs") {
    bindSingleton<FileSystemService> { FileSystemServiceImpl() }
}
