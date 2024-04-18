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
    // Definition der Variablen, die im Fragment verwendet werden
    private lateinit var binding: FragmentRecipeDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Fragment görünümünün şişirilmesi ve bağlama nesnesinin başlatılması
        // Inflation der Fragmentansicht und Initialisierung des Bindungsobjekts
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'deki yemek detay verisi gözlemleniyor
        // Beobachtung der Detaildaten der Mahlzeit im ViewModel
        viewModel.mealDetailLiveData.observe(viewLifecycleOwner) { meal ->
            updateUI(meal) // UI güncelleniyor
            // Aktualisierung der Benutzeroberfläche

            // ViewModel'deki favori yemekler listesi gözlemleniyor
            // Beobachtung der Liste der Lieblingsmahlzeiten im ViewModel
            viewModel.favouriteMealsLiveData.observe(viewLifecycleOwner) { favouriteMeals ->
                var isFavorite = false // Favori olup olmadığını tutacak değişken
                // Variable zur Speicherung des Favoritenstatus

                // Favori yemekler listesinde mevcut yemeğin olup olmadığını kontrol etme
                // Überprüfung, ob die aktuelle Mahlzeit in der Liste der Lieblingsmahlzeiten vorhanden ist
                for (favouriteMeal in favouriteMeals) {
                    if (favouriteMeal.idMeal == meal.idMeal) {
                        isFavorite = true // Yemek favorilerde bulundu
                        // Mahlzeit wurde in den Favoriten gefunden
                        continue // Döngüden çık
                        // Schleife verlassen
                    }
                }

                // Favori durumuna göre ilgili ikonun atanması
                // Zuweisung des entsprechenden Icons basierend auf dem Favoritenstatus
                binding.addrecipe.setImageResource(if (isFavorite) R.drawable.favoritefill else R.drawable.favoriteempty)

                // Favori ekleme/çıkarma butonunun tıklama olayı
                // Klickereignis für die Schaltfläche zum Hinzufügen/Entfernen von Favoriten
                binding.addrecipe.setOnClickListener {
                    if (isFavorite) {
                        // Eğer yemek zaten favorilerde ise, favorilerden çıkar
                        // Wenn die Mahlzeit bereits zu den Favoriten gehört, wird sie entfernt
                        val favoriteMeal = FavoriteMeal(meal.idMeal, meal.strMeal, meal.strMealThumb, meal.strCategory, meal.strArea, meal.strInstructions)
                        viewModel.deleteFavoriteMeal(favoriteMeal)
                        Toast.makeText(context, "${meal.strMeal} Aus den Favoriten entfernt", Toast.LENGTH_SHORT).show()
                    } else {
                        // Yemek favorilerde değilse, favorilere ekle
                        // Wenn die Mahlzeit nicht zu den Favoriten gehört, wird sie hinzugefügt
                        val favoriteMeal = FavoriteMeal(meal.idMeal, meal.strMeal, meal.strMealThumb, meal.strCategory, meal.strArea, meal.strInstructions)
                        viewModel.addFavoriteMeal(favoriteMeal)
                        Toast.makeText(context, "${meal.strMeal} Zu den Favoriten hinzugefügt", Toast.LENGTH_SHORT).show()
                    }
                    // Favori durumu değiştikten sonra UI güncellemesi
                    // Aktualisierung der Benutzeroberfläche nach Änderung des Favoritenstatus
                    isFavorite = !isFavorite
                    binding.addrecipe.setImageResource(if (isFavorite) R.drawable.favoritefill else R.drawable.favoriteempty)
                }
            }
        }
    }

    // Yemek detaylarının UI'da gösterilmesi için kullanılan fonksiyon
    // Funktion, die verwendet wird, um die Details der Mahlzeit in der Benutzeroberfläche anzuzeigen
    private fun updateUI(meal: Meal?) {
        meal?.let {
            // Yemek detaylarının atanması
            // Zuweisung der Mahlzeitdetails
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
            // Klickereignis für die Schaltfläche zum Öffnen des YouTube-Links
            binding.ytbbtn.setOnClickListener {
                meal.strYoutube?.let { youtubeLink ->
                    openYouTubeLink(youtubeLink)
                }
            }
        }
    }

    // YouTube linkini açmak için kullanılan fonksiyon
    // Funktion, die verwendet wird, um den YouTube-Link zu öffnen
    private fun openYouTubeLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    // Yemeğin malzemelerini döndüren fonksiyon
    // Funktion, die die Zutaten der Mahlzeit zurückgibt
    private fun getIngredients(meal: Meal): String {
        return listOfNotNull(
            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3).joinToString(", ")
    }

    // Yemeğin ölçülerini döndüren fonksiyon
    // Funktion, die die Maße der Mahlzeit zurückgibt
    private fun getMeasures(meal: Meal): String {
        return listOfNotNull(
            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3).joinToString(", ")
    }

}

