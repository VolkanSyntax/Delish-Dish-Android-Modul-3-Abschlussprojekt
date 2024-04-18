package de.syntax.androidabschluss.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax.androidabschluss.data.models.CocktailList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Kokteyl bilgilerine erişim için temel URL.
// Basis-URL für den Zugriff auf Cocktailinformationen.
const val BASE_URL_COCKTAILS = "https://www.thecocktaildb.com/api/json/v1/1/"

// Moshi JSON parser'ı için yapılandırma.
// Konfiguration für den Moshi JSON-Parser.
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()) // Kotlin sınıflarıyla uyumluluğu sağlar. // Stellt die Kompatibilität mit Kotlin-Klassen sicher.
    .build()

// HTTP isteklerinde logging (günlükleme) için yapılandırma.
// Konfiguration für das Logging von HTTP-Anfragen.
private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // İstek ve yanıtların detaylarını günlükleme seviyesi. // Log-Level für Details von Anfragen und Antworten.
}

// OkHttpClient, HTTP istekleri için yapılandırılır.
// OkHttpClient wird für HTTP-Anfragen konfiguriert.
private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger) // Logging interceptor'ı eklenir. // Logging-Interceptor wird hinzugefügt.
    .build()

// Retrofit istemcisi, Moshi JSON converter'ı ve OkHttpClient ile yapılandırılır.
// Retrofit-Client wird mit Moshi JSON-Converter und OkHttpClient konfiguriert.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi)) // Moshi kullanarak JSON işleme. // Verarbeitet JSON mit Moshi.
    .baseUrl(BASE_URL_COCKTAILS) // Temel URL tanımlanır. // Basis-URL wird festgelegt.
    .client(httpClient) // HTTP istemcisi tanımlanır. // HTTP-Client wird definiert.
    .build()

// Retrofit ile HTTP istekleri için arayüz tanımları.
// Interface-Definitionen für HTTP-Anfragen mit Retrofit.
interface CocktailApiService {
    // Belirli bir ID'ye sahip kokteyli getirir.
    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") cocktailIdValue: String): CocktailList // Kokteyl ID'ye göre getirir. // Holt einen Cocktail nach ID.

    // Tüm kokteylleri listeler.
    @GET("search.php?s=")
    suspend fun cocktailsList(): CocktailList // Kokteylleri listeler. // Listet Cocktails.

    // İsme göre kokteyl araması yapar.
    @GET("search.php")
    suspend fun getCocktailByName(@Query("s") cocktailSearchValue: String): CocktailList // İsme göre kokteyl arar. // Sucht einen Cocktail nach Name.
}

// Retrofit servis nesnesinin singleton olarak tanımlanması. Uygulama boyunca bu nesne üzerinden API istekleri yapılır.
// Definition des Retrofit-Service-Objekts als Singleton. API-Anfragen werden über dieses Objekt während der gesamten Anwendungsdauer gemacht.
object CocktailApi {
    val retrofitService: CocktailApiService by lazy { retrofit.create(CocktailApiService::class.java) }
}

