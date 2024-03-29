package de.syntax.androidabschluss.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



// Room veritabanı için ana sınıfı tanımlar. Veritabanında saklanacak entity'ler ve versiyon numarası burada belirtilir.
@Database(entities = [FavoriteCocktail::class], version = 1)
abstract class FavoriteCocktailDatabase : RoomDatabase() {
    // Veritabanı işlemleri için DAO'yu döndüren abstract bir property.
    abstract val favoriteCocktailDao: FavoriteCocktailDao
}

// Veritabanı nesnesinin tek bir instance'ını tutacak değişken.
private lateinit var INSTANCE: FavoriteCocktailDatabase

// Veritabanı instance'ını döndüren veya oluşturan fonksiyon.
fun getCocktailDatabase(context: Context): FavoriteCocktailDatabase {
    // Sınıf düzeyinde eşzamanlılık kontrolü sağlar. Aynı anda birden fazla thread'in veritabanı instance'ını oluşturmasını engeller.
    synchronized(FavoriteCocktailDatabase::class.java) {
        // INSTANCE değişkeni daha önce başlatılmamışsa yeni bir veritabanı instance'ı oluşturur.
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext, // Application context kullanarak uygulama kapsamında bir veritabanı oluşturur.
                FavoriteCocktailDatabase::class.java, // Oluşturulacak veritabanı sınıfı.
                "favorite_cocktail_database" // Veritabanı dosyasının adı.
            ).build()
        }
        // Oluşturulan veya var olan veritabanı instance'ını döndürür.
        return INSTANCE
    }
}
