package de.syntax.androidabschluss.data.models.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.syntax.androidabschluss.data.models.Message


@JsonClass(generateAdapter = true) //Moshi'nin bu sınıf için bir JSON adapteri otomatik olarak üretmesini sağlar.
                                  // Veranlasst Moshi, automatisch einen JSON-Adapter für diese Klasse zu generieren.**/
// ChatRequest sınıfının tanımı, mesajlar ve model bilgisini içerir.
// Definition der ChatRequest-Klasse, die Informationen über Nachrichten und das Modell enthält.**/
data class ChatRequest(
    @Json(name = "messages") val messages: List<Message>, //Chat mesajlarını temsil eden bir liste. JSON anahtar olarak "messages" kullanılır.
                                                         // Liste, die Chat-Nachrichten darstellt. Verwendet "messages" als JSON-Schlüssel.**/
    @Json(name = "model") val model: String // Kullanılacak modelin adını belirten bir string. JSON anahtar olarak "model" kullanılır.
                                           // Zeichenkette, die den Namen des verwendeten Modells angibt. Verwendet "model" als JSON-Schlüssel.**/
)