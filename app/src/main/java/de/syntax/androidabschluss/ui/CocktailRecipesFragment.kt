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
import de.syntax.androidabschluss.adapters.CocktailAdapter
import de.syntax.androidabschluss.databinding.FragmentCocktailRecipesBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel

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

        binding.assistant.setOnClickListener {
            findNavController().navigate(R.id.assistantFragment)
        }

        binding.cocktailsRV.layoutManager = GridLayoutManager(context,2);
        binding.cocktailsRV.hasFixedSize()

        viewModel.cocktailsLiveData.observe(viewLifecycleOwner){ cocktails ->
            binding.cocktailsRV.adapter = CocktailAdapter(cocktails,viewModel)
        }
        viewModel.getCocktails()

    }
}