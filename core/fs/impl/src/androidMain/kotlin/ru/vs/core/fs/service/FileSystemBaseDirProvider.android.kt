package ru.vs.core.fs.service

import android.content.Context
import kotlinx.io.files.Path
import ru.vladislavsumin.core.di.i

internal actual fun org.kodein.di.DirectDI.createFileSystemBaseDirProvider(): FileSystemBaseDirProvider {
    val context = i<Context>()
    return object : FileSystemBaseDirProvider {
        override fun getBaseDir(): Path = Path(context.dataDir.path)
    }
}