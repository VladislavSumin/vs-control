package ru.vs.control.id

internal object IdFactory {
    fun createId(rawId: String): Id {
        val ids = rawId.split("#").map { SimpleIdImpl(it) }
        return createId(ids)
    }

    fun createId(ids: Iterable<Id>): Id {
        val simpleIds = ids.fold(mutableListOf<Id.SimpleId>()) { acc, id ->
            when (id) {
                is Id.SimpleId -> acc += id
                is Id.CompositeId -> acc += id.parts
            }
            acc
        }
        return createId(simpleIds)
    }

    @Suppress("MagicNumber")
    private fun createId(ids: List<Id.SimpleId>): Id {
        return when {
            ids.isEmpty() -> error("Incorrect parts count")
            ids.size == 1 -> ids[0]
            ids.size == 2 -> DoubleIdImpl(ids[0], ids[1])
            ids.size == 3 -> TripleIdImpl(ids[0], ids[1], ids[2])
            else -> CompositeIdImpl(ids)
        }
    }
}
