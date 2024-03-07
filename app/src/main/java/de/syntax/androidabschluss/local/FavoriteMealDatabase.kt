package de.syntax.androidabschluss.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMeal::class], version = 1)
abstract class FavoriteMealDatabase : RoomDatabase() {
    abstract val favoriteMealDao: FavoriteMealDao
}

private lateinit var INSTANCE: FavoriteMealDatabase

fun getMealDatabase(context: Context): FavoriteMealDatabase {

    synchronized(FavoriteMealDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                FavoriteMealDatabase::class.java,
                "favorite_meal_database"
            ).build()
        }
        return INSTANCE
    }
}