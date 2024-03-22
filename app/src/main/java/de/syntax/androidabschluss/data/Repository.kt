package de.syntax.androidabschluss.data

import android.util.Log
import androidx.lifecycle.LiveData
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.data.remote.CocktailApiService
import de.syntax.androidabschluss.data.remote.RecipeApiService
import de.syntax.androidabschluss.local.FavoriteCocktail
import de.syntax.androidabschluss.local.FavoriteCocktailDatabase
import de.syntax.androidabschluss.local.FavoriteMeal
import de.syntax.androidabschluss.local.FavoriteMealDatabase
import de.syntax.androidabschluss.local.Note
import de.syntax.androidabschluss.local.NoteDatabase


const val TAG = "Repository"
class Repository(private val recipeApiService: RecipeApiService,
                 private val cocktailApiService: CocktailApiService,
                 private val favoriteMealDb: FavoriteMealDatabase,
                 private val favoriteCocktailDb: FavoriteCocktailDatabase,
                 private val noteDb: NoteDatabase)

{

    suspend fun getMeals(): List<Meal>? {
        return try {
            recipeApiService.mealsList().meals
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching meals: $e")
            null
        }
    }

    suspend fun searchMeals(query: String): List<Meal>? {
        return try {
            recipeApiService.mealsSearch(query).meals
        } catch (e: Exception) {
            Log.e(TAG, "Error searching meals: $e")
            null
        }
    }

    suspend fun getMealDetail(mealId: String): Meal? {
        return try {
            recipeApiService.mealDetail(mealId).meals?.firstOrNull()
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching meal detail: $e")
            null
        }
    }

    suspend fun getCocktails(): List<Cocktail>? {
        return try {
            cocktailApiService.cocktailsList().drinks
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching cocktails: $e")
            null
        }
    }

    suspend fun getCocktailByName(query: String): List<Cocktail>? {
        return try {
            cocktailApiService.getCocktailByName(query).drinks
        } catch (e: Exception) {
            Log.e(TAG, "Error searching cocktail by name: $e")
            null
        }
    }

    suspend fun getCocktailById(cocktailId: String): Cocktail? {
        return try {
            cocktailApiService.getCocktailById(cocktailId).drinks?.firstOrNull()
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching cocktail by id: $e")
            null
        }
    }





    // -------Favoriten Meals - Cocktail -- Room - Database-------------Repository---------


    fun getFavouriteMeals(): LiveData<List<FavoriteMeal>> {
        return favoriteMealDb.favoriteMealDao.getFavouriteMeals()
    }

    fun getFavouriteCocktails(): LiveData<List<FavoriteCocktail>> {
        return favoriteCocktailDb.favoriteCocktailDao.getFavoriteCocktails()
    }


    suspend fun insertFavoriteMeal(favoriteMeal: FavoriteMeal) {
        try {
            favoriteMealDb.favoriteMealDao.insertFavoriteMeal(favoriteMeal)
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into insert FavoriteMeal: $e")
        }
    }

    suspend fun insertFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        try {
            favoriteCocktailDb.favoriteCocktailDao.insertFavoriteCocktail(favoriteCocktail)
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into insert favoriteCocktail: $e")
        }
    }



    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) {
        try {
            favoriteMealDb.favoriteMealDao.deleteFavoriteMeal(favoriteMeal)
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into delete FavoriteMeal: $e")
        }
    }

    suspend fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        try {
            favoriteCocktailDb.favoriteCocktailDao.deleteFavoriteCocktail(favoriteCocktail)
        } catch (e: Exception){
            Log.e(TAG, "Error writing into delete FavoriteCocktail: $e")
        }
    }




    //------------- Note Room Database---------Repository------------------

    val noteList: LiveData<List<Note>> = noteDb.noteDatabaseDao.getAll()


    suspend fun insert(note: Note) {
        try {
            noteDb.noteDatabaseDao.insert(note)
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into database: $e")
        }
    }

    suspend fun update(note: Note) {
        try {
            noteDb.noteDatabaseDao.update(note)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating database: $e")
        }
    }

    suspend fun delete(note: Note) {
        try {
            noteDb.noteDatabaseDao.deleteById(note.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting from database: $e")
        }
    }


}
