package ru.vs.rsub

open class RSubServerSubscriptionsAbstract {
    protected val impls: MutableMap<String, InterfaceWrapperAbstract> = mutableMapOf()

    fun getImpl(name: String, methodName: String): RSubServerSubscription {
        return impls[name]!!.methodImpls[methodName]!!
    }

    open class InterfaceWrapperAbstract {
        val methodImpls: MutableMap<String, RSubServerSubscription> = mutableMapOf()
    }
}
