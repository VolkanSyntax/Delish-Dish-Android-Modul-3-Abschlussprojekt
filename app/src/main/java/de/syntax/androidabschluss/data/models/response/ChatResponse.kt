package de.syntax.androidabschluss.data.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.syntax.androidabschluss.data.models.Choice


@JsonClass(generateAdapter = true) // Moshi'nin bu sınıf için bir JSON adapteri otomatik olarak üretmesini sağlar.
                                  // Veranlasst Moshi, automatisch einen JSON-Adapter für diese Klasse zu generieren.
// ChatResponse sınıfının tanımı, kullanıcı seçimlerini içerir.
// Definition der ChatResponse-Klasse, die Benutzerauswahlen enthält.
data class ChatResponse(
    @Json(name = "choices") val choices: List<Choice> // Kullanıcı seçimlerini temsil eden bir liste. JSON anahtar olarak "choices" kullanılır.
                                                     // Liste, die Benutzerauswahlen darstellt. Verwendet "choices" als JSON-Schlüssel.
)