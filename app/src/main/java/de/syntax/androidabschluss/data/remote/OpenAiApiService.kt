package de.syntax.androidabschluss.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
const val BASE_URL = "https://api.openai.com/v1/"
const val OPENAI_API_KEY = "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"

// JSON serileştirme/deserileştirme işlemleri için Moshi nesnesi.
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()) // Kotlin sınıflarıyla uyumluluk sağlamak için.
    .build()

// HTTP istemcisi, zaman aşımı ve diğer yapılandırmalar ile.
private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES) // Bağlantı zaman aşımı süresi.
    .readTimeout(60, TimeUnit.SECONDS) // Okuma zaman aşımı süresi.
    .writeTimeout(60, TimeUnit.SECONDS) // Yazma zaman aşımı süresi.
    .build()

// Retrofit istemcisi, Moshi ve OkHttpClient ile yapılandırılır.
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL) // API'nin temel URL'si.
    .client(okHttpClient) // HTTP istemcisi.
    .addConverterFactory(MoshiConverterFactory.create(moshi)) // JSON işlemleri için Moshi kullanılır.
    .build()

// API interface tanımı.
interface ApiInterface {
    @POST("chat/completions")
    fun createChatCompletion(
        @Body chatRequest: ChatRequest,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") authorization: String = "Bearer $OPENAI_API_KEY"
    ): Call<ChatResponse>
}

// API istemcisini sağlayan singleton nesnesi.
object OpenAiApiService {
    val apiInterface: ApiInterface by lazy { retrofit.create(ApiInterface::class.java) }
}
