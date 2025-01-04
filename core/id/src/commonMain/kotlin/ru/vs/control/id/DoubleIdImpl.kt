package ru.vs.control.id

internal class DoubleIdImpl(
    override val firstPart: Id.SimpleId,
    override val secondPart: Id.SimpleId,
) : Id.DoubleId {
    override val rawId: String by lazy { "${firstPart.rawId}#${secondPart.rawId}" }
    override val parts: List<Id.SimpleId> by lazy { listOf(firstPart, secondPart) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DoubleIdImpl

        if (firstPart != other.firstPart) return false
        return secondPart == other.secondPart
    }

    override fun hashCode(): Int {
        var result = firstPart.hashCode()
        result = 31 * result + secondPart.hashCode()
        return result
    }

    override fun toString(): String {
        return "DoubleId($rawId)"
    }
}
