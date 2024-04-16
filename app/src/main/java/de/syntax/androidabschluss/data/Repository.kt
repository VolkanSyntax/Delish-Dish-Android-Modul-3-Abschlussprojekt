package de.syntax.androidabschluss.data

import android.util.Log
import androidx.lifecycle.LiveData
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.data.models.request.ChatRequest
import de.syntax.androidabschluss.data.models.response.ChatResponse
import de.syntax.androidabschluss.data.remote.ApiInterface
import de.syntax.androidabschluss.data.remote.CocktailApiService
import de.syntax.androidabschluss.data.remote.OPENAI_API_KEY
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
class Repository(
    // Yemek tarifleri için API servisi
    private val recipeApiService: RecipeApiService,
    // Kokteyl tarifleri için API servisi
    private val cocktailApiService: CocktailApiService,

    private val chatApiService: ApiInterface,

    // Favori yemekler için yerel veritabanı
    private val favoriteMealDb: FavoriteMealDatabase,
    // Favori kokteyller için yerel veritabanı
    private val favoriteCocktailDb: FavoriteCocktailDatabase,
    // Kullanıcı notları için yerel veritabanı
    private val noteDb: NoteDatabase
) {

    // Yemek tariflerini çeken asenkron fonksiyon
    suspend fun getMeals(): List<Meal>? {
        return try {
            recipeApiService.mealsList().meals // API'den tüm yemeklerin listesini çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching meals: $e") // Hata durumunda loglama yapar
            null // Hata durumunda null döner
        }
    }

    // Belirli bir sorguya göre yemek tariflerini arayan asenkron fonksiyon
    suspend fun searchMeals(query: String): List<Meal> {
        return try {
            recipeApiService.mealsSearch(query).meals ?: emptyList() // API'den sorguya göre yemek tariflerini çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error searching meals: $e") // Hata durumunda loglama yapar
            emptyList() // Hata durumunda boş bir liste döner
        }
    }

    // Belirli bir yemeğin detaylarını çeken asenkron fonksiyon
    suspend fun getMealDetail(mealId: String): Meal? {
        return try {
            recipeApiService.mealDetail(mealId).meals?.firstOrNull() // API'den belirli bir ID'ye sahip yemeğin detaylarını çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching meal detail: $e") // Hata durumunda loglama yapar
            null // Hata durumunda null döner
        }
    }

    // Tüm kokteylleri çeken asenkron fonksiyon
    suspend fun getCocktails(): List<Cocktail>? {
        return try {
            cocktailApiService.cocktailsList().drinks // API'den tüm kokteyllerin listesini çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching cocktails: $e") // Hata durumunda loglama yapar
            emptyList() // Hata durumunda boş bir liste döner
        }
    }

    // Belirli bir sorguya göre kokteylleri arayan asenkron fonksiyon
    suspend fun getCocktailByName(query: String): List<Cocktail> {
        return try {
            cocktailApiService.getCocktailByName(query).drinks ?: emptyList() // API'den sorguya göre kokteylleri çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error searching cocktail by name: $e") // Hata durumunda loglama yapar
            emptyList() // Hata durumunda boş bir liste döner
        }
    }


    // Belirli bir kokteylin detaylarını çeken asenkron fonksiyon
    suspend fun getCocktailById(cocktailId: String): Cocktail? {
        return try {
            cocktailApiService.getCocktailById(cocktailId).drinks?.firstOrNull() // API'den belirli bir ID'ye sahip kokteylin detaylarını çeker
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching cocktail by id: $e") // Hata durumunda loglama yapar
            null // Hata durumunda null döner
        }
    }

    // Favori yemeklerin listesini döndüren fonksiyon
    fun getFavouriteMeals(): LiveData<List<FavoriteMeal>> {
        return favoriteMealDb.favoriteMealDao.getFavouriteMeals() // Yerel veritabanından favori yemeklerin listesini çeker
    }

    // Favori kokteyllerin listesini döndüren fonksiyon
    fun getFavouriteCocktails(): LiveData<List<FavoriteCocktail>> {
        return favoriteCocktailDb.favoriteCocktailDao.getFavoriteCocktails() // Yerel veritabanından favori kokteyllerin listesini çeker
    }

    // Yeni bir favori yemek ekleyen asenkron fonksiyon
    suspend fun insertFavoriteMeal(favoriteMeal: FavoriteMeal) {
        try {
            favoriteMealDb.favoriteMealDao.insertFavoriteMeal(favoriteMeal) // Yerel veritabanına yeni bir favori yemek ekler
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into insert FavoriteMeal: $e") // Hata durumunda loglama yapar
        }
    }

    // Yeni bir favori kokteyl ekleyen asenkron fonksiyon
    suspend fun insertFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        try {
            favoriteCocktailDb.favoriteCocktailDao.insertFavoriteCocktail(favoriteCocktail) // Yerel veritabanına yeni bir favori kokteyl ekler
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into insert favoriteCocktail: $e") // Hata durumunda loglama yapar
        }
    }

    // Belirli bir favori yemeği silen asenkron fonksiyon
    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) {
        try {
            favoriteMealDb.favoriteMealDao.deleteFavoriteMeal(favoriteMeal) // Yerel veritabanından belirli bir favori yemeği siler
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into delete FavoriteMeal: $e") // Hata durumunda loglama yapar
        }
    }

    // Belirli bir favori kokteyli silen asenkron fonksiyon
    suspend fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        try {
            favoriteCocktailDb.favoriteCocktailDao.deleteFavoriteCocktail(favoriteCocktail) // Yerel veritabanından belirli bir favori kokteyli siler
        } catch (e: Exception){
            Log.e(TAG, "Error writing into delete FavoriteCocktail: $e") // Hata durumunda loglama yapar
        }
    }

    // Tüm kullanıcı notlarının listesini döndüren fonksiyon
    val noteList: LiveData<List<Note>> = noteDb.noteDatabaseDao.getAll() // Yerel veritabanından tüm kullanıcı notlarını çeker

    // Yeni bir not ekleyen asenkron fonksiyon
    suspend fun insert(note: Note) {
        try {
            noteDb.noteDatabaseDao.insert(note) // Yerel veritabanına yeni bir not ekler
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into database: $e") // Hata durumunda loglama yapar
        }
    }

    // Belirli bir notu güncelleyen asenkron fonksiyon
    suspend fun update(note: Note) {
        try {
            noteDb.noteDatabaseDao.update(note) // Yerel veritabanında belirli bir notu günceller
        } catch (e: Exception) {
            Log.e(TAG, "Error updating database: $e") // Hata durumunda loglama yapar
        }
    }

    // Belirli bir notu silen asenkron fonksiyon
    suspend fun delete(note: Note) {
        try {
            noteDb.noteDatabaseDao.deleteById(note.id) // Yerel veritabanından belirli bir notu siler
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting from database: $e") // Hata durumunda loglama yapar
        }
    }




    // Chat işlemlerini gerçekleştiren fonksiyon
    fun createChatCompletion(
        chatRequest: ChatRequest,
        onSuccess: (ChatResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        chatApiService.createChatCompletion(chatRequest, "application/json", "Bearer $OPENAI_API_KEY")
            .enqueue(object : Callback<ChatResponse> {
                override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                    if (response.isSuccessful) {
                        onSuccess(response.body())
                    } else {
                        onError("API call successful but returned an error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                    onError("API call failed: ${t.message}")
                }
            })
    }





}
