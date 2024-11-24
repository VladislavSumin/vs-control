package ru.vs.control.server.domain

import io.ktor.network.tls.certificates.buildKeyStore

/**
 * Отвечает за загрузку / формирование ssl сертификатов и связок ключей
 */
internal interface KeyStoreInteractor

internal class KeyStoreInteractorImpl : KeyStoreInteractor {

    fun createKeyStore() {
        buildKeyStore {
            // TODO
        }
    }
}
