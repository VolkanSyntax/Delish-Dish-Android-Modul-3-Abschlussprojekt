package de.syntax.androidabschluss.data.remote.client

import de.syntax.androidabschluss.data.remote.apiinterfaces.ApiInterface
import de.syntax.androidabschluss.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object OpAiClient {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()


    @Volatile
    private var INSTANCE : ApiInterface? = null

    fun getInstance() : ApiInterface {
        synchronized(this){
            return INSTANCE ?: Retrofit.Builder()
                .baseUrl(BASE_URL) //im utils ordner
                .client(okHttpClient)  // timeout
                .addConverterFactory(MoshiConverterFactory.create()) //moshifactory benutzen
                .build()            //
                .create(ApiInterface::class.java)
                .also {
                    INSTANCE = it
                }
        }
    }
}