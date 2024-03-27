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

class ApiBuilder {

    private val apikey = "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"
    private val config = OpenAIConfig(token = apikey, timeout = Timeout(60.seconds))
    private val openAiApi = OpenAI(config)

    fun isMessageAboutRecipesOrCocktails(message: String): Boolean {
        val keywords = listOf(
            "yemek", "tarifi", "kokteyl", // Türkce
            "recipe", "cocktail", // İngilizce
            "Essen", "Rezept", // Almanca
            "comida", "receta", "cóctel", // İspanyolca
            "plat", "recette", // Fransızca
            "cibo", "ricetta", // İtalyanca
            "comida", "receita", // Portekizce
            "еда", "рецепт", // Rusça
            "eten", "recept", // Hollanda dili
            "mat", "recept" // İsveççe
        )
        for (keyword in keywords) {
            if (message.contains(keyword, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    fun senMessageRequestToApi(messages: String): Flow<ChatCompletionChunk> {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.User,
                    content = messages
                )
            )
        )
        return openAiApi.chatCompletions(chatCompletionRequest)

    }


}