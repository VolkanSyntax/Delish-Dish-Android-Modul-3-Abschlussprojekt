package de.syntax.androidabschluss.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao // Bu sınıfın bir Data Access Object olduğunu belirten annotasyon.
interface NoteDatabaseDao {

    // Bir notu veritabanına ekler. Eğer not zaten varsa, üzerine yazar.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    // Veritabanındaki bir notu günceller.
    @Update
    suspend fun update(note: Note)

    // Veritabanındaki tüm notları döndürür. Sonuçlar LiveData olarak sarılır, bu sayede UI katmanı veritabanındaki değişikliklere abone olabilir.
    @Query("SELECT * FROM Note")
    fun getAll(): LiveData<List<Note>>

    // Belirli bir ID'ye sahip notu döndürür. Sonuç LiveData olarak sarılıdır.
    @Query("SELECT * FROM Note WHERE id = :key")
    fun getById(key: Long): LiveData<Note>

    // Belirli bir ID'ye sahip notu veritabanından siler.
    @Query("DELETE FROM Note WHERE id = :id")
    suspend fun deleteById(id: Long)

    // Veritabanındaki tüm notları siler.
    @Query("DELETE FROM Note")
    suspend fun deleteAll()
}
