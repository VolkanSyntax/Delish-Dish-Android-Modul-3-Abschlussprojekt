package de.syntax.androidabschluss.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import de.syntax.androidabschluss.data.models.Message // richtig
import de.syntax.androidabschluss.data.models.request.ChatRequest // richtig
import de.syntax.androidabschluss.data.repository.ChatRepository // richtig


class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val chatRepository = ChatRepository()

    val chatResponse = MutableLiveData<String?>()
    val errorMessage = MutableLiveData<String?>()
    val isLoading = MutableLiveData<Boolean>()

    // Mesajın yemek tarifi veya kokteylle ilgili olup olmadığını kontrol eden işlev
    private fun isMessageAboutRecipesOrCocktails(message: String): Boolean {
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

    fun createChatCompletion(messages: List<Message>, model: String) {
        isLoading.postValue(true)
        val request = ChatRequest(messages, model)

        // Mesajlar içinde yemek tarifi veya kokteyl ile ilgili içerik var mı diye kontrol et
        if (messages.any { message -> isMessageAboutRecipesOrCocktails(message.content) }) {
            chatRepository.createChatCompletion(request, { response ->
                isLoading.postValue(false)
                chatResponse.postValue(response?.choices?.firstOrNull()?.message?.content)
            }, { error ->
                isLoading.postValue(false)
                errorMessage.postValue(error)
            })
        } else {
            isLoading.postValue(false)
            errorMessage.postValue("I can only provide information about Recipes and Cocktails.")
        }
    }
}
