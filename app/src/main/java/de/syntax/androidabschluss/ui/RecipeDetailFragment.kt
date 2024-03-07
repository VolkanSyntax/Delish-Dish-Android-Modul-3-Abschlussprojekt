package de.syntax.androidabschluss.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import coil.load
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.databinding.FragmentRecipeDetailBinding
import de.syntax.androidabschluss.local.FavoriteMeal

class RecipeDetailFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mealDetailLiveData.observe(viewLifecycleOwner) { meal ->
            updateUI(meal)
            binding.addrecipe.setOnClickListener {
                val favoriteMeal = FavoriteMeal(meal.idMeal, meal.strMeal, meal.strMealThumb, meal.strCategory, meal.strArea, meal.strInstructions)
                viewModel.addFavoriteMeal(favoriteMeal)
                Toast.makeText(context, "${meal.strMeal} favorilere eklendi", Toast.LENGTH_SHORT)
                    .show()
            }

            binding.removerecipe.setOnClickListener {
                val favoriteMeal = FavoriteMeal(meal.idMeal, meal.strMeal, meal.strMealThumb, meal.strCategory, meal.strArea, meal.strInstructions)
                viewModel.deleteFavoriteMeal(favoriteMeal)
                Toast.makeText(
                    context,
                    "${meal.strMeal} favorilerden çıkarıldı",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }




    }

    private fun updateUI(meal: Meal?) {
        meal?.let {
            binding.recipeDetailTitle.text = meal.strMeal
            binding.recipeDetailImageView.load(meal.strMealThumb)
            binding.recipeDetailCategory.text = meal.strCategory
            binding.recipeDetailArea.text = meal.strArea
            binding.recipeDetailIngredient.text = getIngredients(meal)
            binding.recipeDetailMeasure.text = getMeasures(meal)
            binding.recipeDetailInstructions.text = meal.strInstructions
            binding.imageView.setOnClickListener {
                meal.strYoutube?.let { youtubeLink ->
                    openYouTubeLink(youtubeLink)
                }
            }
        }
    }

    private fun openYouTubeLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun getIngredients(meal: Meal): String {
        return listOfNotNull(
            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3
        ).joinToString(", ")
    }

    private fun getMeasures(meal: Meal): String {
        return listOfNotNull(
            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3
        ).joinToString(", ")
    }


}
