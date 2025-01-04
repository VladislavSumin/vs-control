package ru.vs.control.feature.servers.client.domain

import kotlin.jvm.JvmInline

@JvmInline
value class ServerId(val raw: Long)

data class Server(
    val id: ServerId = ServerId(0),
    val name: String,
    val isSecure: Boolean,
    val host: String,
    val port: Int,
    val accessToken: String,
)
