package de.syntax.androidabschluss.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// FavoriteMeal entity'sini içeren ve versiyonu 1 olarak belirlenen Room veritabanı tanımı.
@Database(entities = [FavoriteMeal::class], version = 1)
abstract class FavoriteMealDatabase : RoomDatabase() {
    // Veritabanı işlemleri için kullanılacak DAO'nun tanımı.
    abstract val favoriteMealDao: FavoriteMealDao
}

// FavoriteMealDatabase türünde bir singleton instance'ın late init tanımı.
private lateinit var INSTANCE: FavoriteMealDatabase

// Veritabanı instance'ını döndüren veya oluşturan fonksiyon.
fun getMealDatabase(context: Context): FavoriteMealDatabase {
    // Eşzamanlılık kontrolü için synchronized bloğu. Aynı anda yalnızca bir thread bu bloğa girebilir.
    synchronized(FavoriteMealDatabase::class.java) {
        // INSTANCE daha önce başlatılmamışsa yeni bir veritabanı instance'ı oluşturur.
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext, // Uygulamanın context'i kullanılarak.
                FavoriteMealDatabase::class.java, // Oluşturulacak veritabanı sınıfı.
                "favorite_meal_database" // Veritabanı dosyası adı.
            ).build() // Veritabanını oluşturur.
        }
        // Oluşturulan veya var olan veritabanı instance'ını döndürür.
        return INSTANCE
    }
}
