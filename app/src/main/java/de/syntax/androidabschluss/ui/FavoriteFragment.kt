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
    // ViewBinding ile UI bileşenlerine erişim sağlanır.
    // ViewBinding ermöglicht den Zugriff auf UI-Komponenten.
    private lateinit var binding: FragmentFavoriteBinding

    // ViewModel ile veri katmanına erişim.
    // Zugriff auf die Datenebene über das ViewModel.
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Fragment layout'unun inflate işlemi ve binding nesnesinin başlatılması.
        // Inflation des Fragment-Layouts und Initialisierung des Binding-Objekts.
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // Yemekler ve kokteyller için LinearLayoutManager'lar ayarlanır.
        // LinearLayoutManager für Mahlzeiten und Cocktails wird eingestellt.
        val mealsLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val cocktailsLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // RecyclerView'lar için layout manager'ların atanması.
        // Zuweisung von Layout-Managern für RecyclerViews.
        binding.favouriteMealsListRV.layoutManager = mealsLayoutManager
        binding.favouriteCocktailsListRV.layoutManager = cocktailsLayoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Favori yemeklerin gözlemlenmesi ve adapter'a atanması.
        // Beobachtung der favorisierten Mahlzeiten und Zuweisung des Adapters.
        viewModel.favouriteMealsLiveData.observe(viewLifecycleOwner) { favoriteMeals ->
            favoriteMeals?.let {
                // Favori yemekler için özel bir adapter kullanılır.
                // Ein spezieller Adapter wird für favorisierte Mahlzeiten verwendet.
                binding.favouriteMealsListRV.adapter = FavoriteRecipeAdapter(it, viewModel)
            }
        }

        // Favori kokteyllerin gözlemlenmesi ve adapter'a atanması.
        // Beobachtung der favorisierten Cocktails und Zuweisung des Adapters.
        viewModel.favouriteCocktailsLiveData.observe(viewLifecycleOwner) { favoriteCocktails ->
            favoriteCocktails?.let {
                // Favori kokteyller için özel bir adapter kullanılır.
                // Ein spezieller Adapter wird für favorisierte Cocktails verwendet.
                binding.favouriteCocktailsListRV.adapter = FavoriteCocktailAdapter(it, viewModel)
            }
        }
    }
}
