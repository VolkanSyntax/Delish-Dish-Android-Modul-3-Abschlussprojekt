package de.syntax.androidabschluss.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.adapters.RecipeAdapter
import de.syntax.androidabschluss.databinding.FragmentRecipeListBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel

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

        binding.assistant.setOnClickListener {
            findNavController().navigate(R.id.assistantFragment)
        }
        // Layout manager atanıyor
        binding.reciperListRV.layoutManager = GridLayoutManager(context,2);

        binding.reciperListRV.hasFixedSize()

        viewModel.mealsLiveData.observe(viewLifecycleOwner) { meals ->
            binding.reciperListRV.adapter = RecipeAdapter(meals, viewModel)
        }

        viewModel.getMeals()
    }
}
