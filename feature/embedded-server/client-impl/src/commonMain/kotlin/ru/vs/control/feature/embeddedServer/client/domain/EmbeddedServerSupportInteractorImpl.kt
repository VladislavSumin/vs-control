package ru.vs.control.feature.embeddedServer.client.domain

class EmbeddedServerSupportInteractorImpl : EmbeddedServerSupportInteractor {
    override fun isSupportedOnCurrentPlatform(): Boolean = IsSupportedByPlatform
}
