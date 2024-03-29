package de.syntax.androidabschluss.ui

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
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.databinding.FragmentCocktailDetailBinding
import de.syntax.androidabschluss.local.FavoriteCocktail
import de.syntax.androidabschluss.viewmodel.MainViewModel

class CocktailDetailFragment : Fragment() {
    private lateinit var binding: FragmentCocktailDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Fragment layout'unun inflate işlemi ve binding nesnesinin başlatılması.
        binding = FragmentCocktailDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'deki kokteyl detaylarını gözlemleyip UI'ı güncelleme.
        viewModel.cocktailDetailLiveData.observe(viewLifecycleOwner) { cocktail ->
            updateUI(cocktail) // UI güncelleme işlemi.

            // Kokteylin favori olup olmadığını kontrol etme ve UI'ı buna göre güncelleme.
            viewModel.favouriteCocktailsLiveData.observe(viewLifecycleOwner) { favoriteCocktails ->
                var isFavorite = false
                for (favoriteCocktail in favoriteCocktails) {
                    if (favoriteCocktail.idDrink == cocktail.idDrink) {
                        isFavorite = true
                        break
                    }
                }

                // Favori durumuna göre görseli güncelleme.
                binding.addcocktail.setImageResource(if (isFavorite) R.drawable.favoritefill else R.drawable.favoriteempty)

                // Favori ekleme veya çıkarma işlemi.
                binding.addcocktail.setOnClickListener {
                    if (isFavorite) {
                        viewModel.deleteFavoriteCocktail(FavoriteCocktail(cocktail.idDrink, cocktail.strDrink, cocktail.strDrinkThumb, cocktail.strCategory, cocktail.strInstructions))
                        Toast.makeText(context, "${cocktail.strDrink} Aus den Favoriten entfernt", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.addFavoriteCocktail(FavoriteCocktail(cocktail.idDrink, cocktail.strDrink, cocktail.strDrinkThumb, cocktail.strCategory, cocktail.strInstructions))
                        Toast.makeText(context, "${cocktail.strDrink} Zu den Favoriten hinzugefügt", Toast.LENGTH_SHORT).show()
                    }
                    isFavorite = !isFavorite
                }
            }
        }
    }

    // Kokteyl detaylarını göstermek için UI güncelleme.
    private fun updateUI(drinks: Cocktail?){
        drinks?.let {
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

// Kokteylin içindekiler listesini oluşturma.
private fun getIngredients(cocktails: Cocktail): String {
    return listOfNotNull(cocktails.strIngredient1,cocktails.strIngredient2,cocktails.strIngredient3).joinToString (",")
}

// Kokteylin ölçümler listesini oluşturma.
private fun getMeasures(coctail: Cocktail): String{
    return  listOfNotNull(coctail.strMeasure1,coctail.strMeasure2,coctail.strMeasure3).joinToString(",")
}
