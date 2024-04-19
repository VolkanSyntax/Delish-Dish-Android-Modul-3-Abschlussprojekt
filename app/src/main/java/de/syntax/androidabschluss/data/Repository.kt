package de.syntax.androidabschluss.data

import android.util.Log
import androidx.lifecycle.LiveData
import de.syntax.androidabschluss.BuildConfig
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.data.models.request.ChatRequest
import de.syntax.androidabschluss.data.models.response.ChatResponse
import de.syntax.androidabschluss.data.remote.ApiInterface
import de.syntax.androidabschluss.data.remote.CocktailApiService
import de.syntax.androidabschluss.data.remote.RecipeApiService
import de.syntax.androidabschluss.local.FavoriteCocktail
import de.syntax.androidabschluss.local.FavoriteCocktailDatabase
import de.syntax.androidabschluss.local.FavoriteMeal
import de.syntax.androidabschluss.local.FavoriteMealDatabase
import de.syntax.androidabschluss.local.Note
import de.syntax.androidabschluss.local.NoteDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val TAG = "Repository"
// Veri katmanı işlemleri için merkezi bir sınıf. API ve yerel veritabanı işlemlerini yönetir.
// Zentrale Klasse für Datenoperationen. Verwaltet API- und lokale Datenbankoperationen.
class Repository(
    // Yemek tarifleri için API servisi
    // API-Dienst für Rezepte
    private val recipeApiService: RecipeApiService,
    // Kokteyl tarifleri için API servisi
    // API-Dienst für Cocktail-Rezepte
    private val cocktailApiService: CocktailApiService,
    // Chat işlemleri için API servisi
    // API-Dienst für Chat-Operationen
    private val chatApiService: ApiInterface,
    // Favori yemekler için yerel veritabanı
    // Lokale Datenbank für Lieblingsgerichte
    private val favoriteMealDb: FavoriteMealDatabase,
    // Favori kokteyller için yerel veritabanı
    // Lokale Datenbank für Lieblingscocktails
    private val favoriteCocktailDb: FavoriteCocktailDatabase,
    // Kullanıcı notları için yerel veritabanı
    // Lokale Datenbank für Benutzernotizen
    private val noteDb: NoteDatabase
) {




    // Yemek tariflerini çeken asenkron fonksiyon
    // Asynchrone Funktion zum Abrufen von Rezepten
    suspend fun getMeals(): List<Meal>? {
        return try {
            recipeApiService.mealsList().meals // API'den tüm yemeklerin listesini çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching meals: $e") // Hata durumunda loglama yapar
            null // Hata durumunda null döner
        }
    }

    // Belirli bir sorguya göre yemek tariflerini arayan asenkron fonksiyon
    // Asynchrone Funktion zum Suchen von Rezepten basierend auf einer bestimmten Anfrage
    suspend fun searchMeals(query: String): List<Meal> {
        return try {
            recipeApiService.mealsSearch(query).meals ?: emptyList() // API'den sorguya göre yemek tariflerini çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error searching meals: $e") // Hata durumunda loglama yapar
            emptyList() // Hata durumunda boş bir liste döner
        }
    }

    // Belirli bir yemeğin detaylarını çeken asenkron fonksiyon
    // Asynchrone Funktion zum Abrufen von Details eines bestimmten Gerichts
    suspend fun getMealDetail(mealId: String): Meal? {
        return try {
            recipeApiService.mealDetail(mealId).meals?.firstOrNull() // API'den belirli bir ID'ye sahip yemeğin detaylarını çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching meal detail: $e") // Hata durumunda loglama yapar
            null // Hata durumunda null döner
        }
    }

    // Tüm kokteylleri çeken asenkron fonksiyon
    // Asynchrone Funktion zum Abrufen aller Cocktails
    suspend fun getCocktails(): List<Cocktail>? {
        return try {
            cocktailApiService.cocktailsList().drinks // API'den tüm kokteyllerin listesini çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching cocktails: $e") // Hata durumunda loglama yapar
            null // Hata durumunda null döner
        }
    }

    // Belirli bir sorguya göre kokteylleri arayan asenkron fonksiyon
    // Asynchrone Funktion zum Suchen von Cocktails basierend auf einer bestimmten Anfrage
    suspend fun getCocktailByName(query: String): List<Cocktail> {
        return try {
            cocktailApiService.getCocktailByName(query).drinks ?: emptyList() // API'den sorguya göre kokteylleri çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error searching cocktail by name: $e") // Hata durumunda loglama yapar
            emptyList() // Hata durumunda boş bir liste döner
        }
    }

    // Belirli bir kokteylin detaylarını çeken asenkron fonksiyon
    // Asynchrone Funktion zum Abrufen der Details eines bestimmten Cocktails
    suspend fun getCocktailById(cocktailId: String): Cocktail? {
        return try {
            cocktailApiService.getCocktailById(cocktailId).drinks?.firstOrNull() // API'den belirli bir ID'ye sahip kokteylin detaylarını çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching cocktail by id: $e") // Hata durumunda loglama yapar
            null // Hata durumunda null döner
        }
    }

    // Favori yemeklerin listesini döndüren fonksiyon
    // Funktion, die eine Liste von Lieblingsgerichten zurückgibt
    fun getFavouriteMeals(): LiveData<List<FavoriteMeal>> {
        return favoriteMealDb.favoriteMealDao.getFavouriteMeals() // Yerel veritabanından favori yemeklerin listesini çeker
    }

    // Favori kokteyllerin listesini döndüren fonksiyon
    // Funktion, die eine Liste von Lieblingscocktails zurückgibt
    fun getFavouriteCocktails(): LiveData<List<FavoriteCocktail>> {
        return favoriteCocktailDb.favoriteCocktailDao.getFavoriteCocktails() // Yerel veritabanından favori kokteyllerin listesini çeker
    }

    // Yeni bir favori yemek ekleyen asenkron fonksiyon
    // Asynchrone Funktion zum Hinzufügen eines neuen Lieblingsgerichts
    suspend fun insertFavoriteMeal(favoriteMeal: FavoriteMeal) {
        try {
            favoriteMealDb.favoriteMealDao.insertFavoriteMeal(favoriteMeal) // Yerel veritabanına yeni bir favori yemek ekler
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into insert FavoriteMeal: $e") // Hata durumunda loglama yapar
        }
    }

    // Yeni bir favori kokteyl ekleyen asenkron fonksiyon
    // Asynchrone Funktion zum Hinzufügen eines neuen Lieblingscocktails
    suspend fun insertFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        try {
            favoriteCocktailDb.favoriteCocktailDao.insertFavoriteCocktail(favoriteCocktail) // Yerel veritabanına yeni bir favori kokteyl ekler
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into insert favoriteCocktail: $e") // Hata durumunda loglama yapar
        }
    }

    // Belirli bir favori yemeği silen asenkron fonksiyon
    // Asynchrone Funktion zum Löschen eines bestimmten Lieblingsgerichts
    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) {
        try {
            favoriteMealDb.favoriteMealDao.deleteFavoriteMeal(favoriteMeal) // Yerel veritabanından belirli bir favori yemeği siler
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into delete FavoriteMeal: $e") // Hata durumunda loglama yapar
        }
    }

    // Belirli bir favori kokteyli silen asenkron fonksiyon
    // Asynchrone Funktion zum Löschen eines bestimmten Lieblingscocktails
    suspend fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        try {
            favoriteCocktailDb.favoriteCocktailDao.deleteFavoriteCocktail(favoriteCocktail) // Yerel veritabanından belirli bir favori kokteyli siler
        } catch (e: Exception){
            Log.e(TAG, "Error writing into delete FavoriteCocktail: $e") // Hata durumunda loglama yapar
        }
    }

    // Tüm kullanıcı notlarının listesini döndüren fonksiyon
    // Funktion, die eine Liste aller Benutzernotizen zurückgibt
    val noteList: LiveData<List<Note>> = noteDb.noteDatabaseDao.getAll() // Yerel veritabanından tüm kullanıcı notlarını çeker

    // Yeni bir not ekleyen asenkron fonksiyon
    // Asynchrone Funktion zum Hinzufügen einer neuen Notiz
    suspend fun insert(note: Note) {
        try {
            noteDb.noteDatabaseDao.insert(note) // Yerel veritabanına yeni bir not ekler
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into database: $e") // Hata durumunda loglama yapar
        }
    }

    // Belirli bir notu güncelleyen asenkron fonksiyon
    // Asynchrone Funktion zum Aktualisieren einer bestimmten Notiz
    suspend fun update(note: Note) {
        try {
            noteDb.noteDatabaseDao.update(note) // Yerel veritabanında belirli bir notu günceller
        } catch (e: Exception) {
            Log.e(TAG, "Error updating database: $e") // Hata durumunda loglama yapar
        }
    }

    // Belirli bir notu silen asenkron fonksiyon
    // Asynchrone Funktion zum Löschen einer bestimmten Notiz
    suspend fun delete(note: Note) {
        try {
            noteDb.noteDatabaseDao.deleteById(note.id) // Yerel veritabanından belirli bir notu siler
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting from database: $e") // Hata durumunda loglama yapar
        }
    }


    val OPENAI_API_KEY = BuildConfig.OPENAI_API_KEY


    // Chat tamamlama işlemini gerçekleştiren fonksiyon
// Funktion zur Durchführung von Chat-Vervollständigungsoperationen
    fun createChatCompletion(
        chatRequest: ChatRequest, // Chat isteği için gerekli bilgileri içeren nesne
        // Objekt, das die notwendigen Informationen für die Chat-Anfrage enthält
        onSuccess: (ChatResponse?) -> Unit, // İşlem başarılı olduğunda çağrılacak fonksiyon
        // Funktion, die aufgerufen wird, wenn die Operation erfolgreich ist
        onError: (String) -> Unit // İşlem başarısız olduğunda çağrılacak fonksiyon
        // Funktion, die aufgerufen wird, wenn die Operation fehlschlägt
    ) {
        chatApiService.createChatCompletion(chatRequest, "application/json", "Bearer $OPENAI_API_KEY") // API servisi üzerinden chat tamamlama isteğini başlatır.
            // API-Dienst startet die Anfrage zur Chat-Vervollständigung.
            .enqueue(object : Callback<ChatResponse> { // Asenkron işlem başlatılır.
                // Startet einen asynchronen Prozess.
                override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) { // API yanıtı başarıyla geldiğinde
                    // Wird aufgerufen, wenn eine Antwort vom Server erfolgreich empfangen wird
                    if (response.isSuccessful) {
                        onSuccess(response.body()) // Başarılı yanıt verileri ile onSuccess fonksiyonu çağrılır.
                        // Ruft die onSuccess-Funktion mit den erfolgreichen Antwortdaten auf
                    } else {
                        onError("API call successful but returned an error: ${response.errorBody()?.string()}") // API başarılı bir şekilde yanıt verdi ancak bir hata içeriyorsa
                        // Ruft die onError-Funktion auf, wenn die API einen Fehler zurückgibt
                    }
                }

                override fun onFailure(call: Call<ChatResponse>, t: Throwable) { // API isteği teknik bir sebepten dolayı başarısız olduğunda
                    // Wird aufgerufen, wenn der API-Aufruf aufgrund eines technischen Fehlers fehlschlägt
                    onError("API call failed: ${t.message}") // Teknik hata mesajı ile onError fonksiyonu çağrılır.
                    // Ruft die onError-Funktion auf, wenn ein technischer Fehler auftritt
                }
            })
    }


}
