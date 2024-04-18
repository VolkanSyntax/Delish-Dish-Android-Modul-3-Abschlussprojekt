package de.syntax.androidabschluss.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true) // Moshi'nin bu sınıf için bir JSON adapteri otomatik olarak üretmesini sağlar.
                                  // Veranlasst Moshi, automatisch einen JSON-Adapter für diese Klasse zu generieren.
data class Message(
    @Json(name = "content") val content: String, // Mesajın içeriğini belirtir. JSON anahtar olarak "content" kullanılır.
                                                // Gibt den Inhalt der Nachricht an. Verwendet "content" als JSON-Schlüssel.
    @Json(name = "role") val role: String // Mesajın gönderildiği rolü belirtir. JSON anahtar olarak "role" kullanılır.
                                         // Gibt die Rolle an, von der die Nachricht gesendet wird. Verwendet "role" als JSON-Schlüssel.
)