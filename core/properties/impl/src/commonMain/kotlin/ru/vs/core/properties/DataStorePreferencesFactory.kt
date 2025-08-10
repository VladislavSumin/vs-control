package ru.vs.core.properties

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okio.Path.Companion.toPath
import ru.vs.core.coroutines.DispatchersProvider
import ru.vs.core.fs.service.FileSystemService

internal interface DataStorePreferencesFactory {
    fun create(name: String): DataStore<Preferences>
}

internal class DataStorePreferencesFactoryImpl(
    private val fileSystemService: FileSystemService,
    private val dispatchersProvider: DispatchersProvider,
) : DataStorePreferencesFactory {
    override fun create(name: String): DataStore<Preferences> {
        val path = fileSystemService.getPreferencesPath("$name.preferences_pb").toString().toPath()
        return PreferenceDataStoreFactory.createWithPath(
            scope = CoroutineScope(dispatchersProvider.io + SupervisorJob()),
        ) { path }
    }
}
