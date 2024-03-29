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
const val BASE_URL_COCKTAILS = "https://www.thecocktaildb.com/api/json/v1/1/"

// Moshi JSON parser'ı için yapılandırma.
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()) // Kotlin sınıflarıyla uyumluluğu sağlar.
    .build()

// HTTP isteklerinde logging (günlükleme) için yapılandırma.
private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // İstek ve yanıtların detaylarını günlükleme seviyesi.
}

// OkHttpClient, HTTP istekleri için yapılandırılır.
private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger) // Logging interceptor'ı eklenir.
    .build()

// Retrofit istemcisi, Moshi JSON converter'ı ve OkHttpClient ile yapılandırılır.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi)) // Moshi kullanarak JSON işleme.
    .baseUrl(BASE_URL_COCKTAILS) // Temel URL tanımlanır.
    .client(httpClient) // HTTP istemcisi tanımlanır.
    .build()

// Retrofit ile HTTP istekleri için arayüz tanımları.
interface CocktailApiService {
    // Belirli bir ID'ye sahip kokteyli getirir.
    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") cocktailIdValue: String): CocktailList

    // Tüm kokteylleri listeler.
    @GET("search.php?s=")
    suspend fun cocktailsList(): CocktailList

    // İsme göre kokteyl araması yapar.
    @GET("search.php")
    suspend fun getCocktailByName(@Query("s") cocktailSearchValue: String): CocktailList
}

// Retrofit servis nesnesinin singleton olarak tanımlanması. Uygulama boyunca bu nesne üzerinden API istekleri yapılır.
object CocktailApi {
    val retrofitService: CocktailApiService by lazy { retrofit.create(CocktailApiService::class.java) }
}

