package de.syntax.androidabschluss.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax.androidabschluss.data.models.MealList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Yemek tarifleri için temel URL adresi.
const val BASE_URL_MEALS = "https://www.themealdb.com/api/json/v1/1/" // Yemek tarifleri için API'nin temel URL'si. // Basis-URL der API für Rezepte.

// JSON serileştirme/deserileştirme işlemleri için Moshi nesnesi.
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()) // Kotlin sınıflarıyla uyumluluk sağlamak için. // Für die Kompatibilität mit Kotlin-Klassen.
    .build() // Moshi nesnesi oluşturulur. // Erstellt ein Moshi-Objekt.

// HTTP isteklerinde günlükleme yapmak için interceptor.
private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // İstek ve yanıtların detaylarını loglar. // Loggt die Details von Anfragen und Antworten.
}

// HTTP istemcisi, günlükleyici interceptor ile yapılandırılır.
private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger) // Günlükleme için interceptor eklenir. // Fügt einen Interceptor für das Logging hinzu.
    .build() // OkHttpClient nesnesi oluşturulur. // Erstellt ein OkHttpClient-Objekt.

// Retrofit istemcisi, Moshi ve OkHttpClient ile yapılandırılır.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi)) // JSON işlemleri için Moshi kullanılır. // Verwendet Moshi für JSON-Operationen.
    .baseUrl(BASE_URL_MEALS) // API'nin temel URL'si belirtilir. // Legt die Basis-URL der API fest.
    .client(httpClient) // HTTP istemcisi. // HTTP-Client.
    .build() // Retrofit istemcisi oluşturulur. // Erstellt einen Retrofit-Client.

// TheMealDB API'sine yapılan istekleri tanımlayan interface.
interface RecipeApiService{
    // Belirli bir kelimeye göre yemek tariflerini arar.
    @GET("search.php")
    suspend fun mealsSearch(@Query("s") mealSearchValue: String): MealList // Yemek arama işlemi. // Suchvorgang für Gerichte.

    // Tüm yemek tariflerini listeler.
    @GET("search.php?s=")
    suspend fun mealsList(): MealList // Yemek listeleme işlemi. // Listet Gerichte auf.

    // Belirli bir ID'ye sahip yemeğin detaylarını getirir.
    @GET("lookup.php")
    suspend fun mealDetail(@Query("i") mealIdValue: String): MealList // Yemek detaylarını getirme işlemi. // Ruft Details eines Gerichts ab.
}

// Retrofit servisi için singleton erişim noktası.
object RecipeApi {
    val retrofitService: RecipeApiService by lazy { retrofit.create(RecipeApiService::class.java) } // Lazy initialization ile API servisi oluşturulur. // Erstellt den API-Dienst mit Lazy-Initialization.
}
