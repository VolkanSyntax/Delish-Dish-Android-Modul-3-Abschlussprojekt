package de.syntax.androidabschluss.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true) // Moshi'nin bu sınıf için bir JSON adapteri otomatik olarak üretmesini sağlar.
                                  // Veranlasst Moshi, automatisch einen JSON-Adapter für diese Klasse zu generieren.
data class Choice(
    @Json(name = "index") val index: Int, // Seçimin indeksini belirtir. JSON anahtar olarak "index" kullanılır.
                                         // Gibt den Index der Auswahl an. Verwendet "index" als JSON-Schlüssel.
    @Json(name = "message") val message: Message // İlişkili mesajı içerir. JSON anahtar olarak "message" kullanılır.
                                                // Enthält die zugehörige Nachricht. Verwendet "message" als JSON-Schlüssel.
)