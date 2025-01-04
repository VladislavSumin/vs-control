package ru.vs.control.id

internal class CompositeIdImpl(override val parts: List<Id.SimpleId>) : Id.CompositeId {
    init {
        // We use SimpleId for one part and DoubleId for two parts
        check(parts.size > 2) { "Incorrect parts count" }
    }

    override val rawId: String by lazy { parts.joinToString(separator = "#") { it.rawId } }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CompositeIdImpl

        return parts == other.parts
    }

    override fun hashCode(): Int {
        return parts.hashCode()
    }

    override fun toString(): String {
        return "CompositeId($rawId)"
    }
}
