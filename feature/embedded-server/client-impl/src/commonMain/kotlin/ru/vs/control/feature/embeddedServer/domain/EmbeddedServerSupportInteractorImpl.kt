package ru.vs.control.feature.embeddedServer.domain

class EmbeddedServerSupportInteractorImpl : EmbeddedServerSupportInteractor {
    override fun isSupportedOnCurrentPlatform(): Boolean = false
}
