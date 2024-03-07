package de.syntax.androidabschluss.data

import androidx.lifecycle.LiveData
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.data.remote.CocktailApiService
import de.syntax.androidabschluss.data.remote.RecipeApiService
import de.syntax.androidabschluss.local.FavoriteCocktail
import de.syntax.androidabschluss.local.FavoriteCocktailDatabase
import de.syntax.androidabschluss.local.FavoriteMeal
import de.syntax.androidabschluss.local.FavoriteMealDatabase

class Repository(private val recipeApiService: RecipeApiService, private val cocktailApiService: CocktailApiService,
                 private val favoriteMealDb: FavoriteMealDatabase, private val favoriteCocktailDb: FavoriteCocktailDatabase
) {

    suspend fun getMeals(): List<Meal>? {
        return try {
            recipeApiService.mealsList().meals
        } catch (e: Exception) {
            null
        }
    }

    suspend fun searchMeals(query: String): List<Meal>? {
        return try {
            recipeApiService.mealsSearch(query).meals
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getMealDetail(mealId: String): Meal? {
        return try {
            recipeApiService.mealDetail(mealId).meals?.firstOrNull()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getCocktails(): List<Cocktail>? {
        return try {
            cocktailApiService.cocktailsList().drinks
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getCocktailByName(query: String): List<Cocktail>? {
        return try {
            cocktailApiService.getCocktailByName(query).drinks
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getCocktailById(cocktailId: String): Cocktail? {
        return try {
            cocktailApiService.getCocktailById(cocktailId).drinks?.firstOrNull()
        } catch (e: Exception) {
            null
        }
    }



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
            // Handle the exception
        }
    }

    suspend fun insertFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        try {
            favoriteCocktailDb.favoriteCocktailDao.insertFavoriteCocktail(favoriteCocktail)
        } catch (e: Exception) {
            // Handle the exception
        }
    }



    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) {
        try {
            favoriteMealDb.favoriteMealDao.deleteFavoriteMeal(favoriteMeal)
        } catch (e: Exception) {
            // Handle the exception
        }
    }

    suspend fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        try {
            favoriteCocktailDb.favoriteCocktailDao.deleteFavoriteCocktail(favoriteCocktail)
        } catch (e: Exception){

        }
    }


}
