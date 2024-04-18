package de.syntax.androidabschluss.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Note sınıfını içeren ve veritabanı sürümü 1 olarak belirtilen Room veritabanı tanımı.
// Definiert eine Room-Datenbank, die die Klasse Note enthält und als Datenbankversion 1 festgelegt ist.
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    // Veritabanı işlemleri için kullanılacak DAO'nun tanımı.
    // Definiert das DAO, das für Datenbankoperationen verwendet wird.
    abstract val noteDatabaseDao: NoteDatabaseDao
}

// NoteDatabase türünde bir singleton instance'ın late init tanımı.
// Definiert eine spät initialisierte Singleton-Instanz der Klasse NoteDatabase.
private lateinit var INSTANCE: NoteDatabase

// Veritabanı instance'ını döndüren veya oluşturan fonksiyon.
// Funktion, die eine Instanz der Datenbank zurückgibt oder erstellt.
fun getNoteDatabase(context: Context): NoteDatabase {
    // Eşzamanlılık kontrolü için synchronized bloğu.
    // Synchronisierter Block zur Kontrolle der Nebenläufigkeit.
    synchronized(NoteDatabase::class.java) {
        // INSTANCE daha önce başlatılmamışsa yeni bir veritabanı instance'ı oluşturur.
        // Erstellt eine neue Datenbankinstanz, wenn INSTANCE noch nicht initialisiert wurde.
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext, // Uygulamanın context'i kullanılarak.
                // Verwendet den Anwendungskontext.
                NoteDatabase::class.java, // Oluşturulacak veritabanı sınıfı.
                // Die zu erstellende Datenbankklasse.
                "note_database" // Veritabanı dosyası adı.
                // Der Name der Datenbankdatei.
            ).build() // Veritabanını oluşturur.
            // Erstellt die Datenbank.
        }
        // Oluşturulan veya var olan veritabanı instance'ını döndürür.
        // Gibt die erstellte oder vorhandene Datenbankinstanz zurück.
        return INSTANCE
    }
}
