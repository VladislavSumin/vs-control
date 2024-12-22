package ru.vs.rsub.server

import ru.vs.rsub.RSubServerSubscriptions
import ru.vs.rsub.TestInterface

@RSubServerSubscriptions([TestInterface::class])
interface TestServerSubscriptions
