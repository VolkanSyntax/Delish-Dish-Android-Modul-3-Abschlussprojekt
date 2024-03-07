package de.syntax.androidabschluss

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.syntax.androidabschluss.data.Repository
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.data.remote.CocktailApi
import de.syntax.androidabschluss.data.remote.RecipeApi
import de.syntax.androidabschluss.local.FavoriteCocktail
import de.syntax.androidabschluss.local.FavoriteMeal
import de.syntax.androidabschluss.local.getCocktailDatabase
import de.syntax.androidabschluss.local.getMealDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(
        RecipeApi.retrofitService, CocktailApi.retrofitService, getMealDatabase(application),
        getCocktailDatabase(application)
    )

    private val _mealsLiveData = MutableLiveData<List<Meal>>()
    val mealsLiveData: LiveData<List<Meal>> = _mealsLiveData

    private val _cocktailsLiveData = MutableLiveData<List<Cocktail>>()
    val cocktailsLiveData: LiveData<List<Cocktail>> = _cocktailsLiveData

    private val _mealDetailLiveData = MutableLiveData<Meal>()
    val mealDetailLiveData: LiveData<Meal> = _mealDetailLiveData

    private val _cocktailDetailLiveData = MutableLiveData<Cocktail>()
    val cocktailDetailLiveData: LiveData<Cocktail> = _cocktailDetailLiveData

    private val _favouriteMealsLiveData = MutableLiveData<List<FavoriteMeal>>()
    val favouriteMealsLiveData: LiveData<List<FavoriteMeal>> = _favouriteMealsLiveData

    private val _favouriteCocktailsLiveData = MutableLiveData<List<FavoriteCocktail>>()
    val favouriteCocktailsLiveData: LiveData<List<FavoriteCocktail>> = _favouriteCocktailsLiveData

    init {
        getFavouriteMeals()
        getFavouriteCocktails()
    }

    fun getMeals() {
        viewModelScope.launch {
            _mealsLiveData.postValue(repository.getMeals())
        }
    }

    fun searchMeals(query: String) {
        viewModelScope.launch {
            _mealsLiveData.postValue(repository.searchMeals(query))
        }
    }

    fun getMealDetail(mealId: String) {
        viewModelScope.launch {
            _mealDetailLiveData.postValue(repository.getMealDetail(mealId))
        }
    }

    fun getCocktails() {
        viewModelScope.launch {
            _cocktailsLiveData.postValue(repository.getCocktails())
        }
    }

    fun searchCocktails(query: String) {
        viewModelScope.launch {
            _cocktailsLiveData.postValue(repository.getCocktailByName(query))
        }
    }

    fun getCocktailDetail(cocktailId: String) {
        viewModelScope.launch {
            _cocktailDetailLiveData.postValue(repository.getCocktailById(cocktailId))
        }
    }

    fun getFavouriteMeals() {
        viewModelScope.launch {
            repository.getFavouriteMeals().observeForever {
                _favouriteMealsLiveData.postValue(it)
            }
        }
    }

    fun getFavouriteCocktails(){
        viewModelScope.launch {
            repository.getFavouriteCocktails().observeForever {
                _favouriteCocktailsLiveData.postValue(it)
            }
        }
    }

    fun addFavoriteMeal(favoriteMeal: FavoriteMeal) {
        viewModelScope.launch {
            repository.insertFavoriteMeal(favoriteMeal)
            getFavouriteMeals()
        }
    }

    fun addFavoriteCocktail(favoriteCocktail: FavoriteCocktail){
        viewModelScope.launch {
            repository.insertFavoriteCocktail(favoriteCocktail)
            getFavouriteCocktails()
        }
    }



    fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) {
        viewModelScope.launch {
            repository.deleteFavoriteMeal(favoriteMeal)
            getFavouriteMeals()
        }
    }

    fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        viewModelScope.launch {
            repository.deleteFavoriteCocktail(favoriteCocktail)
            getFavouriteCocktails()
        }
    }


}
