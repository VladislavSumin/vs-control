package ru.vs.control.id

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object IdSerializer : KSerializer<Id> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "ru.vs.control.id.Id",
        PrimitiveKind.STRING,
    )

    override fun deserialize(decoder: Decoder): Id {
        val rawId = decoder.decodeString()
        return Id(rawId)
    }

    override fun serialize(encoder: Encoder, value: Id) {
        encoder.encodeString(value.rawId)
    }
}
