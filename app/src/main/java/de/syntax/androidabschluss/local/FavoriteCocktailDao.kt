package de.syntax.androidabschluss.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


// FavoriteCocktail nesneleri üzerinde veritabanı işlemleri yapmak için kullanılan DAO arayüzü.
@Dao
interface FavoriteCocktailDao {
    // Yeni bir favori kokteyl ekler. Eğer eklenen kokteylin ID'si zaten veritabanında varsa, mevcut kaydı günceller.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCocktail(favoriteCocktail: FavoriteCocktail)

    // Veritabanındaki tüm favori kokteylleri LiveData olarak döndürür. Bu sayede veritabanındaki değişiklikler otomatik olarak gözlemlenebilir.
    @Query("SELECT * FROM favorite_cocktails")
    fun getFavoriteCocktails(): LiveData<List<FavoriteCocktail>>

    // Belirli bir favori kokteyli veritabanından siler.
    @Delete
    suspend fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail)
}
