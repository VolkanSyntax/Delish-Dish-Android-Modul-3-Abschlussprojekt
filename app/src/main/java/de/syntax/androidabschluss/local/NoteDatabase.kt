package de.syntax.androidabschluss.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Note sınıfını içeren ve veritabanı sürümü 1 olarak belirtilen Room veritabanı tanımı.
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    // Veritabanı işlemleri için kullanılacak DAO'nun tanımı.
    abstract val noteDatabaseDao: NoteDatabaseDao
}

// NoteDatabase türünde bir singleton instance'ın late init tanımı.
private lateinit var INSTANCE: NoteDatabase

// Veritabanı instance'ını döndüren veya oluşturan fonksiyon.
fun getNoteDatabase(context: Context): NoteDatabase {
    // Eşzamanlılık kontrolü için synchronized bloğu.
    synchronized(NoteDatabase::class.java) {
        // INSTANCE daha önce başlatılmamışsa yeni bir veritabanı instance'ı oluşturur.
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext, // Uygulamanın context'i kullanılarak.
                NoteDatabase::class.java, // Oluşturulacak veritabanı sınıfı.
                "note_database" // Veritabanı dosyası adı.
            ).build() // Veritabanını oluşturur.
        }
        // Oluşturulan veya var olan veritabanı instance'ını döndürür.
        return INSTANCE
    }
}
