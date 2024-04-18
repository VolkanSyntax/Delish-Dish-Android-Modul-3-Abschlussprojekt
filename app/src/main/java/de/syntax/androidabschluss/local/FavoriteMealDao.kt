package de.syntax.androidabschluss.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Favori yemeklerle ilgili veritabanı işlemlerini tanımlayan DAO arayüzü.
// Definiert ein DAO-Interface für Datenbankoperationen bezüglich der Lieblingsmahlzeiten.
@Dao
interface FavoriteMealDao {
    // Yeni bir favori yemek ekler. Eğer eklenen yemeğin ID'si zaten varsa, mevcut kayıtla değiştirilir.
    // Fügt eine neue Lieblingsmahlzeit hinzu. Falls die ID der hinzugefügten Mahlzeit bereits existiert, wird der vorhandene Datensatz ersetzt.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMeal(favoriteMeal: FavoriteMeal)

    // Veritabanındaki tüm favori yemekleri LiveData olarak döndürür. Bu sayede, veritabanındaki değişiklikler UI'a dinamik olarak yansıtılır.
    // Gibt alle Lieblingsmahlzeiten in der Datenbank als LiveData zurück. Dadurch werden Änderungen in der Datenbank dynamisch im UI reflektiert.
    @Query("SELECT * FROM favorite_meals")
    fun getFavouriteMeals(): LiveData<List<FavoriteMeal>>

    // Belirli bir favori yemeği veritabanından siler.
    // Löscht eine bestimmte Lieblingsmahlzeit aus der Datenbank.
    @Delete
    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal)
}
