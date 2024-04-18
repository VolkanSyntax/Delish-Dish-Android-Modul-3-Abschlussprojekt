package de.syntax.androidabschluss.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// FavoriteCocktail nesneleri üzerinde veritabanı işlemleri yapmak için kullanılan DAO arayüzü.
// DAO-Interface zur Durchführung von Datenbankoperationen auf FavoriteCocktail-Objekten.
@Dao
interface FavoriteCocktailDao {
    // Yeni bir favori kokteyl ekler. Eğer eklenen kokteylin ID'si zaten veritabanında varsa, mevcut kaydı günceller.
    // Fügt einen neuen Lieblingscocktail hinzu. Wenn die ID des hinzugefügten Cocktails bereits in der Datenbank vorhanden ist, wird der vorhandene Datensatz aktualisiert.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCocktail(favoriteCocktail: FavoriteCocktail)

    // Veritabanındaki tüm favori kokteylleri LiveData olarak döndürür. Bu sayede veritabanındaki değişiklikler otomatik olarak gözlemlenebilir.
    // Gibt alle Lieblingscocktails in der Datenbank als LiveData zurück. Dies ermöglicht es, Änderungen in der Datenbank automatisch zu beobachten.
    @Query("SELECT * FROM favorite_cocktails")
    fun getFavoriteCocktails(): LiveData<List<FavoriteCocktail>>

    // Belirli bir favori kokteyli veritabanından siler.
    // Löscht einen bestimmten Lieblingscocktail aus der Datenbank.
    @Delete
    suspend fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail)
}
