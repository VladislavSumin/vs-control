package ru.vs.core.logger.default

import ru.vs.core.logger.manager.ExternalLoggerFactory

internal expect fun createPlatformLoggerFactory(): ExternalLoggerFactory
