package de.syntax.androidabschluss.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import de.syntax.androidabschluss.adapters.FavoriteCocktailAdapter
import de.syntax.androidabschluss.adapters.FavoriteRecipeAdapter
import de.syntax.androidabschluss.databinding.FragmentFavoriteBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding // ViewBinding ile UI bileşenlerine erişim sağlanır.
    private val viewModel: MainViewModel by activityViewModels() // ViewModel ile veri katmanına erişim.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Fragment layout'unun inflate işlemi ve binding nesnesinin başlatılması.
        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        // Yemekler ve kokteyller için LinearLayoutManager'lar ayarlanır.
        val mealsLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val cocktailsLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // RecyclerView'lar için layout manager'ların atanması.
        binding.favouriteMealsListRV.layoutManager = mealsLayoutManager
        binding.favouriteCocktailsListRV.layoutManager = cocktailsLayoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Favori yemeklerin gözlemlenmesi ve adapter'a atanması.
        viewModel.favouriteMealsLiveData.observe(viewLifecycleOwner) { favoriteMeals ->
            favoriteMeals?.let {
                // Favori yemekler için özel bir adapter kullanılır.
                binding.favouriteMealsListRV.adapter = FavoriteRecipeAdapter(it, viewModel)
            }
        }

        // Favori kokteyllerin gözlemlenmesi ve adapter'a atanması.
        viewModel.favouriteCocktailsLiveData.observe(viewLifecycleOwner) { favoriteCocktails ->
            favoriteCocktails?.let {
                // Favori kokteyller için özel bir adapter kullanılır.
                binding.favouriteCocktailsListRV.adapter = FavoriteCocktailAdapter(it, viewModel)
            }
        }
    }
}
