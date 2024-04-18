package de.syntax.androidabschluss.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.syntax.androidabschluss.data.Repository
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.data.models.Message
import de.syntax.androidabschluss.data.models.request.ChatRequest
import de.syntax.androidabschluss.data.remote.CocktailApi
import de.syntax.androidabschluss.data.remote.OpenAiApiService
import de.syntax.androidabschluss.data.remote.RecipeApi
import de.syntax.androidabschluss.local.FavoriteCocktail
import de.syntax.androidabschluss.local.FavoriteMeal
import de.syntax.androidabschluss.local.Note
import de.syntax.androidabschluss.local.getCocktailDatabase
import de.syntax.androidabschluss.local.getMealDatabase
import de.syntax.androidabschluss.local.getNoteDatabase
import kotlinx.coroutines.launch


// Ana ViewModel sınıfı, AndroidViewModel'den türetilmiştir ve uygulama bağlamını kullanır.
// Haupt-ViewModel-Klasse, abgeleitet von AndroidViewModel, verwendet den Anwendungskontext.
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Repository başlatılır ve API servisleri ile yerel veritabanları tanımlanır.
    // Repository wird initialisiert und API-Dienste sowie lokale Datenbanken werden definiert.
    private val repository = Repository(
        RecipeApi.retrofitService,
        CocktailApi.retrofitService,
        OpenAiApiService.apiInterface,
        getMealDatabase(application),
        getCocktailDatabase(application),
        getNoteDatabase(application)
    )

    // Yemeklerle ilgili verileri saklamak için MutableLiveData tanımlanır ve dışarıya LiveData olarak sunulur.
    // MutableLiveData wird definiert, um Daten über Mahlzeiten zu speichern, und als LiveData nach außen bereitgestellt.
    private val _mealsLiveData = MutableLiveData<List<Meal>>()
    val mealsLiveData: LiveData<List<Meal>>
        get() = _mealsLiveData

    // Kokteyllerle ilgili verileri saklamak için MutableLiveData tanımlanır ve dışarıya LiveData olarak sunulur.
    // MutableLiveData wird definiert, um Daten über Cocktails zu speichern, und als LiveData nach außen bereitgestellt.
    private val _cocktailsLiveData = MutableLiveData<List<Cocktail>>()
    val cocktailsLiveData: LiveData<List<Cocktail>>
        get() = _cocktailsLiveData

    // Seçili bir yemeğin detaylarını saklamak için MutableLiveData tanımlanır ve dışarıya LiveData olarak sunulur.
    // MutableLiveData wird definiert, um Details einer ausgewählten Mahlzeit zu speichern, und als LiveData nach außen bereitgestellt.
    private val _mealDetailLiveData = MutableLiveData<Meal>()
    val mealDetailLiveData: LiveData<Meal>
        get() = _mealDetailLiveData

    // Seçili bir kokteylin detaylarını saklamak için MutableLiveData tanımlanır ve dışarıya LiveData olarak sunulur.
    // MutableLiveData wird definiert, um Details eines ausgewählten Cocktails zu speichern, und als LiveData nach außen bereitgestellt.
    private val _cocktailDetailLiveData = MutableLiveData<Cocktail>()
    val cocktailDetailLiveData: LiveData<Cocktail>
        get() = _cocktailDetailLiveData

    // Kullanıcının favori yemeklerini saklamak için MutableLiveData tanımlanır ve dışarıya LiveData olarak sunulur.
    // MutableLiveData wird definiert, um Lieblingsmahlzeiten des Benutzers zu speichern, und als LiveData nach außen bereitgestellt.
    private val _favouriteMealsLiveData = MutableLiveData<List<FavoriteMeal>>()
    val favouriteMealsLiveData: LiveData<List<FavoriteMeal>>
        get() = _favouriteMealsLiveData

    // Kullanıcının favori kokteyllerini saklamak için MutableLiveData tanımlanır ve dışarıya LiveData olarak sunulur.
    // MutableLiveData wird definiert, um Lieblingscocktails des Benutzers zu speichern, und als LiveData nach außen bereitgestellt.
    private val _favouriteCocktailsLiveData = MutableLiveData<List<FavoriteCocktail>>()
    val favouriteCocktailsLiveData: LiveData<List<FavoriteCocktail>>
        get() = _favouriteCocktailsLiveData


    init {
        // Uygulama başlatıldığında favori yemek ve kokteyllerin yüklenmesi.
        // Lädt die Lieblingsmahlzeiten und -cocktails beim Start der Anwendung.
        getFavouriteMeals()
        getFavouriteCocktails()
    }

    private var TAG = "MainViewModel" // Loglama için kullanılan etiket.

    // Yemek listesini çekmek için kullanılan fonksiyon.
// Funktion zum Abrufen der Mahlzeitenliste.
    fun getMeals() {
        viewModelScope.launch { // Coroutine scope.
            try {
                _mealsLiveData.postValue(repository.getMeals()) // Repository'den yemeklerin çekilip LiveData'ya postalanması.
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching meals: $e") // Hata olması durumunda loglama.
            }
        }
    }

    // Belirli bir sorguya göre yemek araması yapmak için kullanılan fonksiyon.
// Funktion zur Suche nach Mahlzeiten basierend auf einer bestimmten Anfrage.
    fun searchMeals(query: String) {
        viewModelScope.launch { // Coroutine scope.
            try {
                _mealsLiveData.postValue(repository.searchMeals(query)) // Repository'den yemeklerin aratılıp sonuçların LiveData'ya postalanması.
            } catch (e: Exception) {
                Log.e(TAG, "Error searching meals: $e") // Hata olması durumunda loglama.
            }
        }
    }

    // Yemek detayının alınması için fonksiyon
// Funktion, um die Details einer Mahlzeit abzurufen
    fun getMealDetail(mealId: String) {
        viewModelScope.launch { // Coroutine scope başlatılır.
            try {
                _mealDetailLiveData.postValue(repository.getMealDetail(mealId)) // Repository'den belirli bir yemek ID'sine göre detayların çekilmesi.
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching meal detail: $e") // Hata durumunda hata loglanır.
            }
        }
    }

    // Kokteyl listesinin alınması için fonksiyon
// Funktion, um die Liste der Cocktails abzurufen
    fun getCocktails() {
        viewModelScope.launch { // Coroutine scope başlatılır.
            try {
                _cocktailsLiveData.postValue(repository.getCocktails()) // Repository'den kokteyl listesinin çekilmesi.
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching cocktails: $e") // Hata durumunda hata loglanır.
            }
        }
    }

    // Kokteyl araması yapma fonksiyonu
// Funktion zur Suche nach Cocktails
    fun searchCocktails(query: String) {
        viewModelScope.launch { // Coroutine scope başlatılır.
            try {
                _cocktailsLiveData.postValue(repository.getCocktailByName(query)) // Belirli bir sorguya göre kokteyllerin aranması.
            } catch (e: Exception) {
                Log.e(TAG, "Error searching cocktails: $e") // Hata durumunda hata loglanır.
            }
        }
    }


    // Kokteyl detayının alınması için fonksiyon
// Funktion zum Abrufen der Cocktail-Details
    fun getCocktailDetail(cocktailId: String) {
        viewModelScope.launch { // Coroutine scope başlatılır.
            try {
                _cocktailDetailLiveData.postValue(repository.getCocktailById(cocktailId)) // Repository'den belirli bir kokteyl ID'sine göre detayların çekilmesi.
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching cocktail detail: $e") // Hata durumunda hata loglanır.
            }
        }
    }

    // Favori yemeklerin alınması için fonksiyon
// Funktion zum Abrufen der Lieblingsmahlzeiten
    fun getFavouriteMeals() {
        viewModelScope.launch { // Coroutine scope başlatılır.
            try {
                repository.getFavouriteMeals().observeForever { // Repository'den favori yemeklerin çekilmesi ve LiveData'ya postalanması.
                    _favouriteMealsLiveData.postValue(it)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching favourite meals: $e") // Hata durumunda hata loglanır.
            }
        }
    }

    // Favori kokteyllerin alınması için fonksiyon
// Funktion zum Abrufen der Lieblingscocktails
    fun getFavouriteCocktails(){
        viewModelScope.launch { // Coroutine scope başlatılır.
            try {
                repository.getFavouriteCocktails().observeForever { // Repository'den favori kokteyllerin çekilmesi ve LiveData'ya postalanması.
                    _favouriteCocktailsLiveData.postValue(it)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching favourite cocktails: $e") // Hata durumunda hata loglanır.
            }
        }
    }


    // Favori bir yemek eklemek için fonksiyon
// Funktion zum Hinzufügen einer Lieblingsmahlzeit
    fun addFavoriteMeal(favoriteMeal: FavoriteMeal) {
        viewModelScope.launch { // Coroutine başlatılır
            try {
                repository.insertFavoriteMeal(favoriteMeal) // Repository aracılığıyla favori yemek eklenir
                getFavouriteMeals() // Favori yemek listesi güncellenir
            } catch (e: Exception) {
                Log.e(TAG, "Error inserting favorite meal: $e") // Hata durumunda loglama yapılır
            }
        }
    }

    // Favori bir kokteyl eklemek için fonksiyon
// Funktion zum Hinzufügen eines Lieblingscocktails
    fun addFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        viewModelScope.launch { // Coroutine başlatılır
            try {
                repository.insertFavoriteCocktail(favoriteCocktail) // Repository aracılığıyla favori kokteyl eklenir
                getFavouriteCocktails() // Favori kokteyller listesi güncellenir
            } catch (e: Exception) {
                Log.e(TAG, "Error inserting favorite cocktail: $e") // Hata durumunda loglama yapılır
            }
        }
    }

    // Bir favori yemeği silmek için fonksiyon
// Funktion zum Löschen einer Lieblingsmahlzeit
    fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) {
        viewModelScope.launch { // Coroutine başlatılır
            try {
                repository.deleteFavoriteMeal(favoriteMeal) // Repository aracılığıyla favori yemek silinir
                getFavouriteMeals() // Favori yemek listesi güncellenir
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting favorite meal: $e") // Hata durumunda loglama yapılır
            }
        }
    }

    // Bir favori kokteyli silmek için fonksiyon
// Funktion zum Löschen eines Lieblingscocktails
    fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        viewModelScope.launch { // Coroutine başlatılır
            try {
                repository.deleteFavoriteCocktail(favoriteCocktail) // Repository aracılığıyla favori kokteyl silinir
                getFavouriteCocktails() // Favori kokteyller listesi güncellenir
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting favorite cocktail: $e") // Hata durumunda loglama yapılır
            }
        }
    }



    //----------------------------------Firebase---------------------------------------------------



    // Firebase ile kullanıcı işlemleri
// Benutzeroperationen mit Firebase
    private val firebaseAuth = FirebaseAuth.getInstance() // Firebase Auth instance'ını alır.

    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser) // Aktif kullanıcıyı LiveData olarak saklar.
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser // Aktif kullanıcıyı döndüren LiveData'yı sağlar.

    // Kullanıcı kaydı için fonksiyon
// Funktion zur Benutzerregistrierung
    fun signup(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { user ->
            if (user.isSuccessful) {
                login(email, password) // Kayıt başarılı ise giriş yapar.
            } else {
                Log.e(TAG, "Signup failed: ${user.exception}") // Kayıt başarısız olursa hata loglar.
            }
        }
    }

    // Kullanıcı girişi için fonksiyon
// Funktion für Benutzerlogin
    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUser.value = firebaseAuth.currentUser // Başarılı giriş sonrası currentUser güncellenir.
            } else {
                Log.e(TAG, "Login failed: ${it.exception}") // Giriş başarısız olursa hata loglar.
            }
        }
    }

    // Kullanıcı çıkışı için fonksiyon
// Funktion zum Benutzerlogout
    fun logout() {
        firebaseAuth.signOut() // Firebase Auth üzerinden çıkış yapar.
        _currentUser.value = null // currentUser değerini null yapar.
    }


//----------------------------Note-------------------------------------------------



    // Notlar ile ilgili işlemler
// Operationen bezüglich Notizen
    val noteList = repository.noteList // Repository'den notların listesini alır.

    private val _complete = MutableLiveData<Boolean>() // İşlemin tamamlanma durumunu tutar.
    val complete: LiveData<Boolean>
        get() = _complete // İşlem tamamlanma durumunu dışarıya açar.

    // Not ekleme fonksiyonu
// Funktion zum Hinzufügen einer Notiz
    fun insertNote(note: Note) {
        viewModelScope.launch { // Coroutine başlatılır.
            try {
                repository.insert(note) // Repository aracılığıyla not ekler.
                _complete.value = true // İşlem başarılı olduğunda tamamlanma durumu güncellenir.
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error inserting note: $e") // Hata durumunda loglama yapılır.
            }
        }
    }

    // Not güncelleme fonksiyonu
// Funktion zum Aktualisieren einer Notiz
    fun updateNote(note: Note) {
        viewModelScope.launch { // Coroutine başlatılır.
            try {
                repository.update(note) // Repository aracılığıyla not güncellenir.
                _complete.value = true // İşlem başarılı olduğunda tamamlanma durumu güncellenir.
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error updating note with id: ${note.id}, error: $e") // Hata durumunda loglama yapılır.
            }
        }
    }

    // Not silme fonksiyonu
// Funktion zum Löschen einer Notiz
    fun deleteNote(note: Note) {
        viewModelScope.launch { // Coroutine başlatılır.
            try {
                repository.delete(note) // Repository aracılığıyla not silinir.
                Log.e("MainViewModel", "Deleted note with id: ${note.id}") // Silme işlemi loglanır.
                _complete.value = true // İşlem başarılı olduğunda tamamlanma durumu güncellenir.
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error deleting note with id: ${note.id}, error: $e") // Hata durumunda loglama yapılır.
            }
        }
    }

    // İşlem tamamlandıktan sonra durumu sıfırlama
// Status zurücksetzen, nachdem die Operation abgeschlossen ist
    fun unsetComplete() {
        _complete.value = false // İşlem tamamlandıktan sonra durum sıfırlanır.
    }


//-------------------------- OpenAiAssistant--------------------------------------------

    // Chat ile ilgili işlemleri yöneten repository nesnesi
// Repository-Objekt für Chat-Operationen
    private val chatRepository = repository

    // Chat yanıtını tutmak için kullanılan LiveData
// LiveData, die die Chat-Antwort hält
    val chatResponse = MutableLiveData<String?>()

    // Hata mesajlarını tutmak için kullanılan LiveData
// LiveData, die Fehlermeldungen hält
    val errorMessage = MutableLiveData<String?>()

    // İşlemin yüklenme durumunu gösteren LiveData
// LiveData, die den Ladezustand der Operation anzeigt
    val isLoading = MutableLiveData<Boolean>()

    // Anahtar kelimeler listesi
   // Liste von Schlüsselwörtern
    private val keywords = listOf(
        "yemek", "tarifi", "kokteyl", "hazırlanır", "yapılışı", // Türkçe
        "recipe", "cocktail", "food", "meal", // İngilizce
        "Essen", "Rezept", "Lebensmittelrezept", "Nahrungsmittelrezept",
        "Kochrezept", // Almanca
    )

    // Mesajın yemek tarifi veya kokteylle ilgili olup olmadığını kontrol eden fonksiyon
// Funktion, die prüft, ob die Nachricht mit Rezepten oder Cocktails zu tun hat
    private fun isMessageAboutRecipesOrCocktails(message: String): Boolean {
        // Mesaj içinde bu anahtar kelimelerden herhangi birinin geçip geçmediğini kontrol eder.
        // Überprüft, ob die Nachricht eines der Schlüsselwörter enthält.
        for (keyword in keywords) {
            if (message.contains(keyword, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    // Chat tamamlama işlemini gerçekleştiren fonksiyon
    // Funktion zur Durchführung der Chat-Vervollständigung
    fun createChatCompletion(messages: List<Message>, model: String) {
        isLoading.postValue(true) // Yükleniyor durumunu aktif olarak bildirir  // Teilt den aktiven Ladezustand mit
        val request = ChatRequest(messages, model) // Chat isteği oluşturur  // Erstellt eine Chat-Anfrage

        // Mesajlar içinde yemek tarifi veya kokteyl ile ilgili içerik var mı diye kontrol et
        // Überprüft, ob die Nachrichten Inhalte über Rezepte oder Cocktails enthalten
        if (messages.any { message -> isMessageAboutRecipesOrCocktails(message.content) }) {
            chatRepository.createChatCompletion(request, { response ->
                isLoading.postValue(false) // Yükleniyor durumunu pasif olarak bildirir // Setzt den Ladezustand auf inaktiv
                val responseMessage = response?.choices?.firstOrNull()?.message?.content // Yanıttaki ilk seçenekten mesajı çıkarır // Extrahiert die Nachricht aus der ersten Wahl der Antwort
                if (responseMessage != null && isMessageAboutRecipesOrCocktails(responseMessage)) {
                    chatResponse.postValue(responseMessage) // Başarılı yanıtı yayınlar // Veröffentlicht die erfolgreiche Antwort
                } else {
                    // Eğer mesaj yemek tarifi veya kokteylle ilgili değilse, boş bir mesaj döndürülebilir veya isteğe göre farklı bir işlem yapılabilir.
                    // Wenn die Nachricht nichts mit Rezepten oder Cocktails zu tun hat, kann eine leere Nachricht zurückgegeben oder eine andere Aktion durchgeführt werden.
                    chatResponse.postValue(null)
                }
            }, { error ->
                isLoading.postValue(false) // Yükleniyor durumunu pasif olarak bildirir // Setzt den Ladezustand auf inaktiv
                errorMessage.postValue(error) // Hata mesajını yayınlar // Veröffentlicht die Fehlermeldung
            })
        } else {
            isLoading.postValue(false) // Yükleniyor durumunu pasif olarak bildirir // Setzt den Ladezustand auf inaktiv
            errorMessage.postValue("I can only provide information about Recipes and Cocktails.\n" +
                    "\n Ich kann nur Informationen über Rezepte und Cocktails bereitstellen.")
            // Veröffentlicht eine Fehlermeldung, dass nur Informationen über Rezepte und Cocktails bereitgestellt werden können
        }
    }


}
