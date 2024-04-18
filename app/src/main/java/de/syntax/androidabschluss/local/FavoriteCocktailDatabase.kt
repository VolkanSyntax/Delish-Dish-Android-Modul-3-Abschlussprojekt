package de.syntax.androidabschluss.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room veritabanı için ana sınıfı tanımlar. Veritabanında saklanacak entity'ler ve versiyon numarası burada belirtilir.
// Definiert die Hauptklasse für die Room-Datenbank. Entitäten, die in der Datenbank gespeichert werden sollen, und die Versionsnummer werden hier angegeben.
@Database(entities = [FavoriteCocktail::class], version = 1)
abstract class FavoriteCocktailDatabase : RoomDatabase() {
    // Veritabanı işlemleri için DAO'yu döndüren abstract bir property.
    // Eine abstrakte Eigenschaft, die das DAO für Datenbankoperationen zurückgibt.
    abstract val favoriteCocktailDao: FavoriteCocktailDao
}

// Veritabanı nesnesinin tek bir instance'ını tutacak değişken.
// Variable, die eine einzelne Instanz des Datenbankobjekts hält.
private lateinit var INSTANCE: FavoriteCocktailDatabase

// Veritabanı instance'ını döndüren veya oluşturan fonksiyon.
// Funktion, die eine Instanz der Datenbank zurückgibt oder erstellt.
fun getCocktailDatabase(context: Context): FavoriteCocktailDatabase {
    // Sınıf düzeyinde eşzamanlılık kontrolü sağlar. Aynı anda birden fazla thread'in veritabanı instance'ını oluşturmasını engeller.
    // Stellt die Thread-Sicherheit auf Klassenebene sicher. Verhindert, dass mehrere Threads gleichzeitig eine Instanz der Datenbank erstellen.
    synchronized(FavoriteCocktailDatabase::class.java) {
        // INSTANCE değişkeni daha önce başlatılmamışsa yeni bir veritabanı instance'ı oluşturur.
        // Erstellt eine neue Datenbankinstanz, wenn die INSTANCE-Variable noch nicht initialisiert wurde.
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext, // Application context kullanarak uygulama kapsamında bir veritabanı oluşturur.
                // Erstellt eine datenbankweit gültige Datenbank mit dem Anwendungskontext.
                FavoriteCocktailDatabase::class.java, // Oluşturulacak veritabanı sınıfı.
                // Die zu erstellende Datenbankklasse.
                "favorite_cocktail_database" // Veritabanı dosyasının adı.
                // Der Name der Datenbankdatei.
            ).build()
        }
        // Oluşturulan veya var olan veritabanı instance'ını döndürür.
        // Gibt die erstellte oder vorhandene Datenbankinstanz zurück.
        return INSTANCE
    }
}
