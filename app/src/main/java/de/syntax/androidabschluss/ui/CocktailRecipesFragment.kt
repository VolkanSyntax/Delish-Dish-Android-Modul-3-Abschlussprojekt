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
    private val viewModel: MainViewModel by activityViewModels() // ViewModel'e erişim sağlar.
    private lateinit var binding: FragmentCocktailRecipesBinding // ViewBinding ile layout'a erişim.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Layout inflate edilir ve root view döndürülür.
        binding = FragmentCocktailRecipesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Arama butonuna tıklandığında, EditText'ten alınan metinle kokteylleri arama işlemi yapar.
        binding.cocktailListSearchButton.setOnClickListener {
            val searchValues = binding.cocktailListEditTextSearch.text.toString()
            viewModel.searchCocktails(searchValues)
        }

        // Yardımcı asistan ekranına geçiş yapar.
        binding.assistant.setOnClickListener {
            findNavController().navigate(R.id.assistantFragment)
        }

        // RecyclerView için GridLayoutManager ayarlanır, her satırda iki item olacak şekilde.
        binding.cocktailsRV.layoutManager = GridLayoutManager(context,2);
        binding.cocktailsRV.hasFixedSize() // Performansı artırmak için sabit boyut ayarlanır.

        // ViewModel'den kokteyl listesi LiveData'sını gözlemleyip, değişiklik olduğunda RecyclerView adapter'ını günceller.
        viewModel.cocktailsLiveData.observe(viewLifecycleOwner){ cocktails ->
            binding.cocktailsRV.adapter = CocktailAdapter(cocktails,viewModel)
        }
        viewModel.getCocktails() // Kokteyl listesini çekmek için ViewModel'deki fonksiyonu çağırır.
    }
}
