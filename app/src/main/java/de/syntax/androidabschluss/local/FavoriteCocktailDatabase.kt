package de.syntax.androidabschluss.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCocktail::class], version = 1)
abstract class FavoriteCocktailDatabase : RoomDatabase() {
    abstract val favoriteCocktailDao: FavoriteCocktailDao
}

private lateinit var INSTANCE: FavoriteCocktailDatabase


fun getCocktailDatabase(context: Context): FavoriteCocktailDatabase {

    synchronized(FavoriteCocktailDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                FavoriteCocktailDatabase::class.java,
                "favorite_cocktail_database"
            ).build()
        }
        return INSTANCE
    }

}