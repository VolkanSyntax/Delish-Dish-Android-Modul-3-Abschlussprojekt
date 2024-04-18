package de.syntax.androidabschluss.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// FavoriteMeal entity'sini içeren ve versiyonu 1 olarak belirlenen Room veritabanı tanımı.
// Definiert eine Room-Datenbank, die die Entität FavoriteMeal enthält und die auf Version 1 festgelegt ist.
@Database(entities = [FavoriteMeal::class], version = 1)
abstract class FavoriteMealDatabase : RoomDatabase() {
    // Veritabanı işlemleri için kullanılacak DAO'nun tanımı.
    // Definiert das DAO, das für Datenbankoperationen verwendet wird.
    abstract val favoriteMealDao: FavoriteMealDao
}

// FavoriteMealDatabase türünde bir singleton instance'ın late init tanımı.
// Definiert eine spät initialisierte Singleton-Instanz vom Typ FavoriteMealDatabase.
private lateinit var INSTANCE: FavoriteMealDatabase

// Veritabanı instance'ını döndüren veya oluşturan fonksiyon.
// Funktion, die eine Instanz der Datenbank zurückgibt oder erstellt.
fun getMealDatabase(context: Context): FavoriteMealDatabase {
    // Eşzamanlılık kontrolü için synchronized bloğu. Aynı anda yalnızca bir thread bu bloğa girebilir.
    // Synchronisierter Block zur Kontrolle der Nebenläufigkeit. Nur ein Thread kann gleichzeitig in diesen Block eintreten.
    synchronized(FavoriteMealDatabase::class.java) {
        // INSTANCE daha önce başlatılmamışsa yeni bir veritabanı instance'ı oluşturur.
        // Erstellt eine neue Datenbankinstanz, wenn INSTANCE noch nicht initialisiert wurde.
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext, // Uygulamanın context'i kullanılarak.
                // Verwendet den Anwendungskontext.
                FavoriteMealDatabase::class.java, // Oluşturulacak veritabanı sınıfı.
                // Die zu erstellende Datenbankklasse.
                "favorite_meal_database" // Veritabanı dosyası adı.
                // Der Name der Datenbankdatei.
            ).build() // Veritabanını oluşturur.
            // Erstellt die Datenbank.
        }
        // Oluşturulan veya var olan veritabanı instance'ını döndürür.
        // Gibt die erstellte oder vorhandene Datenbankinstanz zurück.
        return INSTANCE
    }
}
