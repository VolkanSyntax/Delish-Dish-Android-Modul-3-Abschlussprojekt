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

const val BASE_URL_COCKTAILS = "https://www.thecocktaildb.com/api/json/v1/1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_COCKTAILS)
    .client(httpClient)
    .build()

interface CocktailApiService {

    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") cocktailIdValue: String): CocktailList

    @GET("search.php?s=")
    suspend fun cocktailsList(): CocktailList

    @GET("search.php")
    suspend fun getCocktailByName(@Query("s") cocktailSearchValue: String): CocktailList

    @GET("lookup.php")
    suspend fun cocktailsFavouriteList(@Query("i") cocktailFavouriteId: Int): CocktailList

}

object CocktailApi {
    val retrofitService: CocktailApiService by lazy { retrofit.create(CocktailApiService::class.java) }
}
