package de.syntax.androidabschluss.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Favori yemeklerle ilgili veritabanı işlemlerini tanımlayan DAO arayüzü.
@Dao
interface FavoriteMealDao {
    // Yeni bir favori yemek ekler. Eğer eklenen yemeğin ID'si zaten varsa, mevcut kayıtla değiştirilir.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMeal(favoriteMeal: FavoriteMeal)

    // Veritabanındaki tüm favori yemekleri LiveData olarak döndürür. Bu sayede, veritabanındaki değişiklikler UI'a dinamik olarak yansıtılır.
    @Query("SELECT * FROM favorite_meals")
    fun getFavouriteMeals(): LiveData<List<FavoriteMeal>>

    // Belirli bir favori yemeği veritabanından siler.
    @Delete
    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal)
}
