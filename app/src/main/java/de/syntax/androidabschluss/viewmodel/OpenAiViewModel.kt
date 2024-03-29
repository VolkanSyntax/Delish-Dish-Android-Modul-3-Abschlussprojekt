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

// OpenAI API'sini kullanarak çeşitli istekler yapabilen ViewModel sınıfı.
class OpenAiViewModel(private val api: ApiBuilder) : ViewModel() {

    // API'den alınan yanıtı tutan LiveData.
    private val _apiResponse = MutableLiveData<String?>()
    val apiResponse: LiveData<String?>
        get() = _apiResponse

    // Kullanıcıdan alınan isteğe göre API'ye istek yapma işlemini yürüten fonksiyon.
    fun getApiResponse(request: String) {
        viewModelScope.launch {
            // İstek, yemek tarifleri veya kokteyllerle ilgili mi diye kontrol ediliyor.
            if (api.isMessageAboutRecipesOrCocktails(request)) {
                try {
                    // İlgili ise, ApiBuilder üzerinden API'ye istek yapılıyor.
                    val response = api.senMessageRequestToApi(request)
                    val stringBuilder = StringBuilder()
                    // API'den gelen yanıtlar birleştiriliyor.
                    response.collect { chunk ->
                        val choices = chunk.choices
                        if (choices.isNotEmpty()) {
                            val completion = choices.last()
                            val delta = completion.delta
                            if (delta?.content != null) {
                                // Yanıt parçaları birleştirilip _apiResponse'a ekleniyor.
                                stringBuilder.append(delta.content.toString())
                                _apiResponse.value = stringBuilder.toString()
                            }
                        }
                    }
                } catch (e: Exception) {
                    // API isteği sırasında hata oluşursa loglanıyor.
                    Log.e("Open API Error", e.message.toString())
                }
            } else {
                // İstek ilgili kategorilerle alakalı değilse kullanıcıya bilgi veriliyor.
                _apiResponse.value = "I can only provide information about Recipes and Cocktails.\n" +
                        "\n Ich kann nur Informationen über Rezepte und Cocktails bereitstellen.\n " +
                        "\n Sadece yemek Tarifleri ve Kokteyller hakkında bilgi verebilirim. "
            }
        }
    }

    // ViewModel'i oluştururken kullanılan fabrika sınıfı.
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // ApiBuilder örneği ViewModel'e enjekte edilerek geri döndürülüyor.
                val api = ApiBuilder()
                return OpenAiViewModel(api = api) as T
            }
        }
    }
}