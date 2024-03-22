package de.syntax.androidabschluss.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.syntax.androidabschluss.data.Repository
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.data.remote.CocktailApi
import de.syntax.androidabschluss.data.remote.RecipeApi
import de.syntax.androidabschluss.local.FavoriteCocktail
import de.syntax.androidabschluss.local.FavoriteMeal
import de.syntax.androidabschluss.local.Note
import de.syntax.androidabschluss.local.getCocktailDatabase
import de.syntax.androidabschluss.local.getMealDatabase
import de.syntax.androidabschluss.local.getNoteDatabase
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(RecipeApi.retrofitService,
        CocktailApi.retrofitService, getMealDatabase(application),
        getCocktailDatabase(application), getNoteDatabase(application)  )





    private val _mealsLiveData = MutableLiveData<List<Meal>>()
    val mealsLiveData: LiveData<List<Meal>>
        get() = _mealsLiveData

    private val _cocktailsLiveData = MutableLiveData<List<Cocktail>>()
    val cocktailsLiveData: LiveData<List<Cocktail>>
        get() = _cocktailsLiveData

    private val _mealDetailLiveData = MutableLiveData<Meal>()
    val mealDetailLiveData: LiveData<Meal>
        get() = _mealDetailLiveData

    private val _cocktailDetailLiveData = MutableLiveData<Cocktail>()
    val cocktailDetailLiveData: LiveData<Cocktail>
        get() = _cocktailDetailLiveData

    private val _favouriteMealsLiveData = MutableLiveData<List<FavoriteMeal>>()
    val favouriteMealsLiveData: LiveData<List<FavoriteMeal>>
        get() = _favouriteMealsLiveData

    private val _favouriteCocktailsLiveData = MutableLiveData<List<FavoriteCocktail>>()
    val favouriteCocktailsLiveData: LiveData<List<FavoriteCocktail>>
        get() = _favouriteCocktailsLiveData

    init {
        getFavouriteMeals()
        getFavouriteCocktails()
    }


    private  var  TAG = "MainViewModel"


    fun getMeals() {
        viewModelScope.launch {
            try {
                _mealsLiveData.postValue(repository.getMeals())
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching meals: $e")
            }
        }
    }


    fun searchMeals(query: String) {
        viewModelScope.launch {
            try {
                _mealsLiveData.postValue(repository.searchMeals(query))
            } catch (e: Exception) {
                Log.e(TAG, "Error searching meals: $e")
            }
        }
    }

    fun getMealDetail(mealId: String) {
        viewModelScope.launch {
            try {
                _mealDetailLiveData.postValue(repository.getMealDetail(mealId))
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching meal detail: $e")
            }
        }
    }

    fun getCocktails() {
        viewModelScope.launch {
            try {
                _cocktailsLiveData.postValue(repository.getCocktails())
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching cocktails: $e")
            }
        }
    }

    fun searchCocktails(query: String) {
        viewModelScope.launch {
            try {
                _cocktailsLiveData.postValue(repository.getCocktailByName(query))
            } catch (e: Exception) {
                Log.e(TAG, "Error searching cocktails: $e")
            }
        }
    }

    fun getCocktailDetail(cocktailId: String) {
        viewModelScope.launch {
            try {
                _cocktailDetailLiveData.postValue(repository.getCocktailById(cocktailId))
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching cocktail detail: $e")
            }
        }
    }

    fun getFavouriteMeals() {
        viewModelScope.launch {
            try {
                repository.getFavouriteMeals().observeForever {
                    _favouriteMealsLiveData.postValue(it)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching favourite meals: $e")
            }
        }
    }

    fun getFavouriteCocktails(){
        viewModelScope.launch {
            try {
                repository.getFavouriteCocktails().observeForever {
                    _favouriteCocktailsLiveData.postValue(it)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching favourite cocktails: $e")
            }
        }
    }

    fun addFavoriteMeal(favoriteMeal: FavoriteMeal) {
        viewModelScope.launch {
            try {
                repository.insertFavoriteMeal(favoriteMeal)
                getFavouriteMeals()
            } catch (e: Exception) {
                Log.e(TAG, "Error inserting favorite meal: $e")
            }
        }
    }

    fun addFavoriteCocktail(favoriteCocktail: FavoriteCocktail){
        viewModelScope.launch {
            try {
                repository.insertFavoriteCocktail(favoriteCocktail)
                getFavouriteCocktails()
            } catch (e: Exception) {
                Log.e(TAG, "Error inserting favorite cocktail: $e")
            }
        }
    }

    fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) {
        viewModelScope.launch {
            try {
                repository.deleteFavoriteMeal(favoriteMeal)
                getFavouriteMeals()
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting favorite meal: $e")
            }
        }
    }

    fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        viewModelScope.launch {
            try {
                repository.deleteFavoriteCocktail(favoriteCocktail)
                getFavouriteCocktails()
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting favorite cocktail: $e")
            }
        }
    }

    //---------------------------Firebase--------------------------


    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)

    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser


    fun signup(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { user ->
            if (user.isSuccessful) {
                login(email, password)
            } else {
                Log.e(TAG, "Signup failed: ${user.exception}")
            }
        }
    }


    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUser.value = firebaseAuth.currentUser
            }else {
                Log.e(TAG, "Login failed: ${it.exception}")
            }
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }

    //-------------------------NoteRoom-----------------------------------------


    val noteList = repository.noteList

    private val _complete = MutableLiveData<Boolean>()
    val complete: LiveData<Boolean>
        get() = _complete

    fun insertNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.insert(note)
                _complete.value = true
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error inserting note: $e")
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.update(note)
                _complete.value = true
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error updating note with id: ${note.id}, error: $e")
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.delete(note)
                Log.e("MainViewModel", "Deleted note with id: ${note.id}")
                _complete.value = true
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error deleting note with id: ${note.id}, error: $e")
            }
        }
    }

    fun unsetComplete() {
        _complete.value = false
    }




}
