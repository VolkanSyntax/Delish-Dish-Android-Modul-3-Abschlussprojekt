package de.syntax.androidabschluss.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import de.syntax.androidabschluss.viewmodel.MainViewModel
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.databinding.FragmentCocktailDetailBinding
import de.syntax.androidabschluss.local.FavoriteCocktail

class CocktailDetailFragment : Fragment() {

    private lateinit var binding: FragmentCocktailDetailBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCocktailDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cocktailDetailLiveData.observe(viewLifecycleOwner) { cocktail ->
            updateUI(cocktail)
            binding.addcocktail.setOnClickListener {
                val favoriteCocktail = FavoriteCocktail(
                    cocktail.idDrink,
                    cocktail.strDrink, cocktail.strDrinkThumb, cocktail.strCategory, cocktail.strInstructions)
                viewModel.addFavoriteCocktail(favoriteCocktail)
                Toast.makeText(
                    context,
                    "${cocktail.strDrink} favorilere eklendi", Toast.LENGTH_SHORT)
                    .show()

            }

            binding.removecocktail.setOnClickListener {
                val favoriteCocktail = FavoriteCocktail(
                    cocktail.idDrink,
                    cocktail.strDrink, cocktail.strDrinkThumb, cocktail.strCategory, cocktail.strInstructions)
                viewModel.deleteFavoriteCocktail(favoriteCocktail)
                Toast.makeText(
                    context,
                    "${cocktail.strDrink} favorilerden cikarildi",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }






    }

    private fun updateUI(drinks: Cocktail?){
        drinks?.let{
            binding.cocktailDetailTitle.text = drinks.strDrink
            binding.cocktailDetailImageView.load(drinks.strDrinkThumb){
                transformations(RoundedCornersTransformation(30f))
            }
            binding.cocktailDetailCategory.text = drinks.strCategory
            binding.cocktailDetailTagsTitle.text = drinks.strTags
            binding.cocktailDetailIngredient.text = getIngredients(drinks)
            binding.cocktailDetailMeasure.text = getMeasures(drinks)
            binding.cocktailDetailInstructions.text = drinks.strInstructions


        }



    }
}

private fun getIngredients(cocktails: Cocktail): String {

    return listOfNotNull(
        cocktails.strIngredient1,cocktails.strIngredient2,cocktails.strIngredient3
    ).joinToString (",")
}

private fun getMeasures(coctail: Cocktail): String{
    return  listOfNotNull(
        coctail.strMeasure1,coctail.strMeasure2,coctail.strMeasure3
    ).joinToString(",")

}


