package de.syntax.androidabschluss.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteCocktailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCocktail(favoriteCocktail: FavoriteCocktail)

    @Query("SELECT * FROM favorite_cocktails")
    fun getFavoriteCocktails(): LiveData<List<FavoriteCocktail>>

    @Delete
    suspend fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail)

}