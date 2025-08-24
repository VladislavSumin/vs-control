package ru.vs.control.id

import kotlinx.serialization.Serializable

/**
 * Represents universal id
 *
 * [Id] format: first_part/firs_part_too#second_part
 * id is sequence of parts divided by #
 * each part is snake_case string
 *
 * [Id] may have any count of part, but we have special forms for most popular cases:
 * @see SimpleId
 * @see CompositeId
 * @see DoubleId
 *
 * @property rawId raw id representation, its equals to serialized state
 */
@Suppress("SERIALIZER_TYPE_INCOMPATIBLE")
@Serializable(IdSerializer::class)
sealed interface Id {
    val rawId: String

    /**
     * Represent id with only one id part
     */
    @Serializable(IdSerializer::class)
    interface SimpleId : Id {
        companion object {
            /**
             * Creates [SimpleId] from snake_case string, if passing more then one parts throws exception
             */
            operator fun invoke(rawId: String): SimpleId = SimpleIdImpl(rawId)
        }
    }

    /**
     * Represent id with 2 or more parts
     *
     * **Note** id with 2 parts always [DoubleId] subtype of [CompositeId], we cant create instance of [CompositeId]
     * with only two parts
     */
    @Serializable(IdSerializer::class)
    interface CompositeId : Id {
        val parts: List<SimpleId>
    }

    /**
     * Represent id with 2 parts
     */
    @Serializable(IdSerializer::class)
    interface DoubleId : CompositeId {
        val firstPart: SimpleId
        val secondPart: SimpleId

        companion object {
            operator fun invoke(firstPart: SimpleId, secondPart: SimpleId): DoubleId =
                DoubleIdImpl(firstPart, secondPart)
        }
    }

    /**
     * Represent id with 3 parts
     */
    @Serializable(IdSerializer::class)
    interface TripleId : CompositeId {
        val firstPart: SimpleId
        val secondPart: SimpleId
        val thirdPart: SimpleId

        companion object {
            operator fun invoke(firstPart: SimpleId, secondPart: SimpleId, thirdPart: SimpleId): TripleId =
                TripleIdImpl(firstPart, secondPart, thirdPart)
        }
    }

    companion object {
        operator fun invoke(rawId: String): Id = IdFactory.createId(rawId)
        operator fun invoke(vararg ids: Id): Id = IdFactory.createId(ids.asIterable())
    }
}
