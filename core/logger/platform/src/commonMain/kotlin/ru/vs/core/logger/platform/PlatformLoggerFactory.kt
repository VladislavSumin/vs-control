package ru.vs.core.logger.platform

import ru.vs.core.logger.manager.ExternalLoggerFactory

internal expect fun createPlatformLoggerFactory(): ExternalLoggerFactory
