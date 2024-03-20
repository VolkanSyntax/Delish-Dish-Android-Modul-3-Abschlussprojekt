package de.syntax.androidabschluss.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM Note")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE id = :key")
    fun getById(key:String): LiveData<Note>

    @Query("DELETE FROM Note WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM Note")
    suspend fun deleteAll()




}