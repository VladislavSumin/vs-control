package ru.vs.core.logger.default

import ru.vs.core.logger.manager.ExternalLogger

internal expect fun createPlatformLogger(): ExternalLogger
