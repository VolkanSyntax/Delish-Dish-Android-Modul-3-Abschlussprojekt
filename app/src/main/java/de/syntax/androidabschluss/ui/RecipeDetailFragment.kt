package de.syntax.androidabschluss.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.databinding.FragmentRecipeDetailBinding
import de.syntax.androidabschluss.local.FavoriteMeal
import de.syntax.androidabschluss.viewmodel.MainViewModel

class RecipeDetailFragment : Fragment() {
    // Fragment içinde kullanılacak değişkenlerin tanımlanması
    private lateinit var binding: FragmentRecipeDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Fragment görünümünün şişirilmesi ve bağlama nesnesinin başlatılması
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'deki yemek detay verisi gözlemleniyor
        viewModel.mealDetailLiveData.observe(viewLifecycleOwner) { meal ->
            updateUI(meal) // UI güncelleniyor

            // ViewModel'deki favori yemekler listesi gözlemleniyor
            viewModel.favouriteMealsLiveData.observe(viewLifecycleOwner) { favouriteMeals ->
                var isFavorite = false // Favori olup olmadığını tutacak değişken

                // Favori yemekler listesinde mevcut yemeğin olup olmadığını kontrol etme
                for (favouriteMeal in favouriteMeals) {
                    if (favouriteMeal.idMeal == meal.idMeal) {
                        isFavorite = true // Yemek favorilerde bulundu
                        continue // Döngüden çık
                    }
                }

                // Favori durumuna göre ilgili ikonun atanması
                binding.addrecipe.setImageResource(if (isFavorite) R.drawable.favoritefill else R.drawable.favoriteempty)

                // Favori ekleme/çıkarma butonunun tıklama olayı
                binding.addrecipe.setOnClickListener {
                    if (isFavorite) {
                        // Eğer yemek zaten favorilerde ise, favorilerden çıkar
                        val favoriteMeal = FavoriteMeal(meal.idMeal, meal.strMeal, meal.strMealThumb, meal.strCategory, meal.strArea, meal.strInstructions)
                        viewModel.deleteFavoriteMeal(favoriteMeal)
                        Toast.makeText(context, "${meal.strMeal} Aus den Favoriten entfernt", Toast.LENGTH_SHORT).show()
                    } else {
                        // Yemek favorilerde değilse, favorilere ekle
                        val favoriteMeal = FavoriteMeal(meal.idMeal, meal.strMeal, meal.strMealThumb, meal.strCategory, meal.strArea, meal.strInstructions)
                        viewModel.addFavoriteMeal(favoriteMeal)
                        Toast.makeText(context, "${meal.strMeal} Zu den Favoriten hinzugefügt", Toast.LENGTH_SHORT).show()
                    }
                    // Favori durumu değiştikten sonra UI güncellemesi
                    isFavorite = !isFavorite
                    binding.addrecipe.setImageResource(if (isFavorite) R.drawable.favoritefill else R.drawable.favoriteempty)
                }
            }
        }
    }

    // Yemek detaylarının UI'da gösterilmesi için kullanılan fonksiyon
    private fun updateUI(meal: Meal?) {
        meal?.let {
            // Yemek detaylarının atanması
            binding.recipeDetailTitle.text = meal.strMeal
            binding.recipeDetailImageView.load(meal.strMealThumb){
                transformations(RoundedCornersTransformation(30f))
            }
            binding.recipeDetailCategory.text = meal.strCategory
            binding.recipeDetailArea.text = meal.strArea
            binding.recipeDetailIngredient.text = getIngredients(meal)
            binding.recipeDetailMeasure.text = getMeasures(meal)
            binding.recipeDetailInstructions.text = meal.strInstructions
            // YouTube linkinin açılması için buton tıklama olayı
            binding.ytbbtn.setOnClickListener {
                meal.strYoutube?.let { youtubeLink ->
                    openYouTubeLink(youtubeLink)
                }
            }
        }
    }

    // YouTube linkini açmak için kullanılan fonksiyon
    private fun openYouTubeLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    // Yemeğin malzemelerini döndüren fonksiyon
    private fun getIngredients(meal: Meal): String {
        return listOfNotNull(
            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3
        ).joinToString(", ")
    }

    // Yemeğin ölçülerini döndüren fonksiyon
    private fun getMeasures(meal: Meal): String {
        return listOfNotNull(
            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3
        ).joinToString(", ")
    }

}
