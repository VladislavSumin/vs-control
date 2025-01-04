package ru.vs.control.id

internal class SimpleIdImpl(override val rawId: String) : Id.SimpleId {
    init {
        check(ID_VERIFICATION_REGEXP.matches(rawId)) { "Incorrect simpleId format, rawId=$rawId" }
    }

    override fun toString(): String {
        return "SimpleId($rawId)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SimpleIdImpl

        return rawId == other.rawId
    }

    override fun hashCode(): Int {
        return rawId.hashCode()
    }

    companion object {
        private const val ID_PART_VERIFICATION_REGEXP = "([a-z0-9]+(_[a-z0-9]+)*)"
        private val ID_VERIFICATION_REGEXP =
            "^$ID_PART_VERIFICATION_REGEXP(/$ID_PART_VERIFICATION_REGEXP.)*\$".toRegex()
    }
}
