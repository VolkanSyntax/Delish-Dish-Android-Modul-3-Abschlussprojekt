package de.syntax.androidabschluss.data.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.syntax.androidabschluss.data.models.Choice


@JsonClass(generateAdapter = true)
data class ChatResponse(
    @Json(name = "choices") val choices: List<Choice>
)
