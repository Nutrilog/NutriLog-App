package com.nutrilog.app.core.data.remote.response

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.nutrilog.app.core.domain.model.Gender
import java.lang.reflect.Type

class GenderAdapter : JsonDeserializer<Gender>, JsonSerializer<Gender> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): Gender? {
        return enumValues<Gender>().firstOrNull { it.name.equals(json.asString, ignoreCase = true) }
    }

    override fun serialize(
        src: Gender,
        typeOfSrc: Type,
        context: JsonSerializationContext,
    ): JsonElement {
        return JsonPrimitive(src.name)
    }
}