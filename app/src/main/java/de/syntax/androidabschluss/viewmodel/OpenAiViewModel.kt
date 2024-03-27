package de.syntax.androidabschluss.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import de.syntax.androidabschluss.data.remote.ApiBuilder
import kotlinx.coroutines.launch

class OpenAiViewModel(private val api: ApiBuilder) : ViewModel(){

    private val _apiResponse = MutableLiveData<String?>()
    val apiResponse: LiveData<String?>
        get() = _apiResponse
    fun getApiResponse(request: String) {
        viewModelScope.launch {
            if (api.isMessageAboutRecipesOrCocktails(request)) {
                // Eğer mesaj tariflerle ilgiliyse, API'ye istek yapılır.
                try {
                    val response = api.senMessageRequestToApi(request)
                    val stringBuilder = StringBuilder()
                    response.collect { chunk ->
                        // Gelen yanıt parçalarını birleştirir.
                        val choices = chunk.choices
                        if (choices.isNotEmpty()) {
                            val completion = choices.last()
                            val delta = completion.delta
                            if (delta?.content != null) {
                                stringBuilder.append(delta.content.toString())
                                // Yanıtı _apiResponse akışına ekler.
                                _apiResponse.value = stringBuilder.toString()
                            }
                        }
                    }
                } catch (e: Exception) {
                    // API'den yanıt alırken bir hata oluşursa loglar.
                    Log.e("Open API Error", e.message.toString())
                }
            } else {
                // Eğer mesaj tariflerle ilgili değilse, belirli bir yanıt döndürür.
                _apiResponse.value = "I can only provide information about Recipes and Cocktails.\n" +
                        "\n Ich kann nur Informationen über Rezepte und Cocktails bereitstellen.\n " +
                        "\n Sadece yemek Tarifleri ve Kokteyller hakkında bilgi verebilirim. "
            }
        }
    }

    // ViewModel'i oluşturmak için kullanılan factory sınıfı.
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // ApiBuilder örneğini ViewModel'e enjekte eder.
                val api = ApiBuilder()
                return OpenAiViewModel(api = api) as T
            }
        }
    }
}