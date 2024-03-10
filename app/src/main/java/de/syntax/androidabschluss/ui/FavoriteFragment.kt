package de.syntax.androidabschluss.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import de.syntax.androidabschluss.viewmodel.MainViewModel
import de.syntax.androidabschluss.adapters.FavoriteCocktailAdapter
import de.syntax.androidabschluss.adapters.FavoriteRecipeAdapter
import de.syntax.androidabschluss.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    // View Binding nesnesi, bu Fragment'a ait view'lara erişim sağlar.
    private lateinit var binding: FragmentFavoriteBinding

    // ViewModel, bu Fragment'ta kullanılan verileri yönetir.
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        // Her RecyclerView için ayrı bir LinearLayoutManager oluşturun
        val mealsLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val cocktailsLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.favouriteMealsListRV.layoutManager = mealsLayoutManager
        binding.favouriteCocktailsListRV.layoutManager = cocktailsLayoutManager

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favouriteMealsLiveData.observe(viewLifecycleOwner) { favoriteMeals ->
            favoriteMeals?.let {
                binding.favouriteMealsListRV.adapter = FavoriteRecipeAdapter(it, viewModel)
            }
        }
        viewModel.favouriteCocktailsLiveData.observe(viewLifecycleOwner) {favoriteCocktails ->
            favoriteCocktails?.let {
                binding.favouriteCocktailsListRV.adapter = FavoriteCocktailAdapter((it),viewModel)
            }

        }
    }
}

