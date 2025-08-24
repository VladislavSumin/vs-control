package ru.vs.control.id

internal class TripleIdImpl(
    override val firstPart: Id.SimpleId,
    override val secondPart: Id.SimpleId,
    override val thirdPart: Id.SimpleId,
) : Id.TripleId {
    override val rawId: String by lazy { "${firstPart.rawId}#${secondPart.rawId}#${thirdPart.rawId}" }
    override val parts: List<Id.SimpleId> by lazy { listOf(firstPart, secondPart, thirdPart) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as TripleIdImpl

        if (firstPart != other.firstPart) return false
        if (secondPart != other.secondPart) return false
        if (thirdPart != other.thirdPart) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstPart.hashCode()
        result = 31 * result + secondPart.hashCode()
        result = 31 * result + thirdPart.hashCode()
        return result
    }

    override fun toString(): String {
        return "TripleId($rawId)"
    }
}
