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

const val BASE_URL_MEALS = "https://www.themealdb.com/api/json/v1/1/"

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
    .baseUrl(BASE_URL_MEALS)
    .client(httpClient)
    .build()

interface RecipeApiService{

    @GET("search.php")
    suspend fun mealsSearch(@Query("s") mealSearchValue: String): MealList

    @GET("search.php?s=")
    suspend fun mealsList(): MealList

    @GET("lookup.php")
    suspend fun mealDetail(@Query("i") mealIdValue: String): MealList



}

object RecipeApi {
    val retrofitService: RecipeApiService by lazy { retrofit.create(RecipeApiService::class.java) }
}
