package de.syntax.androidabschluss.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMeal(favoriteMeal: FavoriteMeal)

    @Query("SELECT * FROM favorite_meals")
    fun getFavouriteMeals(): LiveData<List<FavoriteMeal>>

    @Delete
    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal)
}
