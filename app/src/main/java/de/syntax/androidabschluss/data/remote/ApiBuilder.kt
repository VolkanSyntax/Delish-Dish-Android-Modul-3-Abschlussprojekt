package de.syntax.androidabschluss.data.remote

import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.seconds

// OpenAI API ile iletişim kurmak için bir sınıf tanımlar.
class ApiBuilder {
    // API erişimi için gereken özel anahtar.
    private val apikey = "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"
    // OpenAI istemcisi için yapılandırma ayarları. API anahtarı ve istek zaman aşımı burada belirtilir.
    private val config = OpenAIConfig(token = apikey, timeout = Timeout(60.seconds))
    // Yapılandırma ayarlarıyla başlatılan OpenAI API istemcisi.
    private val openAiApi = OpenAI(config)

    // Verilen mesajın yemek tarifleri veya kokteyllerle ilgili olup olmadığını kontrol eden fonksiyon.
    fun isMessageAboutRecipesOrCocktails(message: String): Boolean {
        // Kontrol edilecek anahtar kelimelerin listesi. Çeşitli dillerde yemek ve kokteylle ilgili kelimeler içerir.
        val keywords = listOf(
            "yemek", "tarifi", "kokteyl","hazirlanir","yapilisi", // Türkçe
            "recipe", "cocktail","food","meal", // İngilizce
            "Essen", "Rezept","Lebensmittelrezept","Nahrungsmittelrezept",
            "Kochrezept",// Almanca
            "comida", "receta", "cóctel", // İspanyolca
            "plat", "recette", // Fransızca
            "cibo", "ricetta", // İtalyanca
            "comida", "receita", // Portekizce
            "еда", "рецепт", // Rusça
            "eten", "recept", // Hollanda dili
            "mat", "recept" // İsveççe
        )
        // Mesaj içinde bu anahtar kelimelerden herhangi birinin gecip geçmediğini kontrol eder.
        for (keyword in keywords) {
            if (message.contains(keyword, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    // Kullanıcıdan alınan mesaja göre OpenAI API'ye bir chat tamamlama isteği gönderen fonksiyon.
    fun senMessageRequestToApi(messages: String): Flow<ChatCompletionChunk> {
        // Chat tamamlama isteği için gerekli parametrelerle ChatCompletionRequest nesnesi oluşturulur.
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"), // Kullanılacak modelin ID'si.
            messages = listOf(
                ChatMessage(
                    role = ChatRole.User, // Mesajın kullanıcıdan geldiğini belirten rol.
                    content = messages // Kullanıcının gönderdiği mesaj içeriği.
                )
            )
        )
        // Oluşturulan istek nesnesi ile OpenAI API'sine chat tamamlama isteği gönderilir ve sonuç bir Flow olarak döndürülür.
        return openAiApi.chatCompletions(chatCompletionRequest)
    }
}
