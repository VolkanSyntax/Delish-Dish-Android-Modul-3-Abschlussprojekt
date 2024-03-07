package de.syntax.androidabschluss.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.adapters.RecipeAdapter
import de.syntax.androidabschluss.databinding.FragmentRecipeListBinding

class RecipeListFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentRecipeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.recipeListSearchButton.setOnClickListener {
            val searchValue = binding.recipeListEditTextSearch.text.toString()
            viewModel.searchMeals(searchValue)
        }
        // Layout manager atanıyor
        binding.reciperListRV.layoutManager = LinearLayoutManager(context)

        viewModel.mealsLiveData.observe(viewLifecycleOwner) { meals ->
            binding.reciperListRV.adapter = RecipeAdapter(meals, viewModel)
        }

        viewModel.getMeals()
    }
}
