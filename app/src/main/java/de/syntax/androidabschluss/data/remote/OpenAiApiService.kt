package de.syntax.androidabschluss.data.remote


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax.androidabschluss.BuildConfig
import de.syntax.androidabschluss.data.models.request.ChatRequest
import de.syntax.androidabschluss.data.models.response.ChatResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

// API anahtarları ve temel URL'ler.
const val BASE_URL = "https://api.openai.com/v1/" // API'nin temel URL'si. // Basis-URL der API.
const val apiKey: String = BuildConfig.API_KEY
// JSON serileştirme/deserileştirme işlemleri için Moshi nesnesi.
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()) // Kotlin sınıflarıyla uyumluluk sağlamak için. // Für die Kompatibilität mit Kotlin-Klassen.
    .build() // Moshi nesnesi oluşturulur. // Erstellt ein Moshi-Objekt.

// HTTP istemcisi, zaman aşımı ve diğer yapılandırmalar ile.
private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES) // Bağlantı zaman aşımı süresi. // Verbindungs-Timeout-Dauer.
    .readTimeout(60, TimeUnit.SECONDS) // Okuma zaman aşımı süresi. // Lese-Timeout-Dauer.
    .writeTimeout(60, TimeUnit.SECONDS) // Yazma zaman aşımı süresi. // Schreib-Timeout-Dauer.
    .build() // OkHttpClient nesnesi oluşturulur. // Erstellt ein OkHttpClient-Objekt.

// Retrofit istemcisi, Moshi ve OkHttpClient ile yapılandırılır.
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL) // API'nin temel URL'si. // Basis-URL der API.
    .client(okHttpClient) // HTTP istemcisi. // HTTP-Client.
    .addConverterFactory(MoshiConverterFactory.create(moshi)) // JSON işlemleri için Moshi kullanılır. // Verwendet Moshi für JSON-Operationen.
    .build() // Retrofit istemcisi oluşturulur. // Erstellt einen Retrofit-Client.

// API interface tanımı.
interface ApiInterface {
    @POST("chat/completions")
    fun createChatCompletion(
        @Body chatRequest: ChatRequest, // ChatRequest nesnesi, HTTP body olarak gönderilir. // ChatRequest-Objekt wird als HTTP-Body gesendet.
        @Header("Content-Type") contentType: String = "application/json", // İçerik tipi başlığı. // Inhalts-Typ-Header.
        @Header("Authorization") authorization: String = "Bearer $apiKey" // Yetkilendirme başlığı. // Autorisierungsheader.
    ): Call<ChatResponse> // ChatResponse tipinde bir cevap döndürür. // Gibt eine Antwort vom Typ ChatResponse zurück.
}

// API istemcisini sağlayan singleton nesnesi.
object OpenAiApiService {
    val apiInterface: ApiInterface by lazy { retrofit.create(ApiInterface::class.java) } // API arayüzü için lazy initialization kullanılarak tanımlanır.
                                                                                        // API-Schnittstelle wird mit Lazy-Initialization definiert.
}
