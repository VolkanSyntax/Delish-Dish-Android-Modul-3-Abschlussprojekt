package de.syntax.androidabschluss.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import de.syntax.androidabschluss.viewmodel.MainViewModel
import de.syntax.androidabschluss.adapters.CocktailAdapter
import de.syntax.androidabschluss.databinding.FragmentCocktailRecipesBinding

class CocktailRecipesFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentCocktailRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCocktailRecipesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cocktailListSearchButton.setOnClickListener {
            val searchValues = binding.cocktailListEditTextSearch.text.toString()
            viewModel.searchCocktails(searchValues)
        }

        binding.cocktailsRV.layoutManager = LinearLayoutManager(context)

        viewModel.cocktailsLiveData.observe(viewLifecycleOwner){ cocktails ->
            binding.cocktailsRV.adapter = CocktailAdapter(cocktails,viewModel)
        }
        viewModel.getCocktails()

    }
}