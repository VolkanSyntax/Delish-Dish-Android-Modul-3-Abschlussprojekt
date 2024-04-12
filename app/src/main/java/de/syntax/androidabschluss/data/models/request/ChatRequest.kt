package de.syntax.androidabschluss.data.models.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.syntax.androidabschluss.data.models.Message


@JsonClass(generateAdapter = true)
data class ChatRequest(
    @Json(name = "messages") val messages: List<Message>,
    @Json(name = "model") val model: String
)