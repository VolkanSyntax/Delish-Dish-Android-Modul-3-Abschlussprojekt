package de.syntax.androidabschluss.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao // Bu sınıfın bir Data Access Object olduğunu belirten annotasyon.
// Annotation, die angibt, dass diese Klasse ein Data Access Object ist.
interface NoteDatabaseDao {

    // Bir notu veritabanına ekler. Eğer not zaten varsa, üzerine yazar.
    // Fügt eine Notiz in die Datenbank ein. Falls die Notiz bereits existiert, wird sie überschrieben.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    // Veritabanındaki bir notu günceller.
    // Aktualisiert eine Notiz in der Datenbank.
    @Update
    suspend fun update(note: Note)

    // Veritabanındaki tüm notları döndürür. Sonuçlar LiveData olarak sarılır, bu sayede UI katmanı veritabanındaki değişikliklere abone olabilir.
    // Gibt alle Notizen in der Datenbank zurück. Die Ergebnisse werden als LiveData umhüllt, so dass die UI-Schicht Änderungen in der Datenbank abonnieren kann.
    @Query("SELECT * FROM Note")
    fun getAll(): LiveData<List<Note>>

    // Belirli bir ID'ye sahip notu döndürür. Sonuç LiveData olarak sarılıdır.
    // Gibt eine Notiz mit einer bestimmten ID zurück. Das Ergebnis wird als LiveData umhüllt.
    @Query("SELECT * FROM Note WHERE id = :key")
    fun getById(key: Long): LiveData<Note>

    // Belirli bir ID'ye sahip notu veritabanından siler.
    // Löscht eine Notiz mit einer bestimmten ID aus der Datenbank.
    @Query("DELETE FROM Note WHERE id = :id")
    suspend fun deleteById(id: Long)

    // Veritabanındaki tüm notları siler.
    // Löscht alle Notizen in der Datenbank.
    @Query("DELETE FROM Note")
    suspend fun deleteAll()
}
