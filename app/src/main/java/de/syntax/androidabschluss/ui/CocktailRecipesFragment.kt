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
import de.syntax.androidabschluss.utils.hideKeyBoard
import de.syntax.androidabschluss.viewmodel.MainViewModel

class CocktailRecipesFragment : Fragment() {
    // ViewModel'e erişim sağlar. Bu ViewModel, kokteyl verilerini ve işlevlerini yönetir.
    // Stellt Zugriff auf das ViewModel bereit, das Cocktaildaten und -funktionen verwaltet.
    private val viewModel: MainViewModel by activityViewModels()

    // ViewBinding ile layout'a erişim sağlanır.
    // Ermöglicht den Zugriff auf das Layout mittels ViewBinding.
    private lateinit var binding: FragmentCocktailRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Layout inflate edilir ve root view döndürülür.
        // Inflates das Layout und gibt die Root-View zurück.
        binding = FragmentCocktailRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Arama butonuna tıklandığında, EditText'ten alınan metinle kokteylleri arama işlemi yapar.
        // Führt eine Cocktailsuche mit dem Text aus dem EditText durch, wenn auf die Suchschaltfläche geklickt wird.
        binding.cocktailListSearchButton.setOnClickListener {
            val searchValues = binding.cocktailListEditTextSearch.text.toString()
            viewModel.searchCocktails(searchValues)
            view.context.hideKeyBoard(it)
        }

        // Yardımcı asistan ekranına geçiş yapar.
        // Navigiert zum Assistentenbildschirm.
        binding.assistant.setOnClickListener {
            findNavController().navigate(R.id.assistantFragment)
        }

        // RecyclerView için GridLayoutManager ayarlanır, her satırda iki item olacak şekilde.
        // Stellt einen GridLayoutManager für den RecyclerView ein, mit zwei Elementen pro Zeile.
        binding.cocktailsRV.layoutManager = GridLayoutManager(context, 2)
        binding.cocktailsRV.hasFixedSize() // Performansı artırmak için sabit boyut ayarlanır.
        // Setzt eine feste Größe zur Leistungssteigerung.

        // ViewModel'den kokteyl listesi LiveData'sını gözlemleyip, değişiklik olduğunda RecyclerView adapter'ını günceller.
        // Beobachtet die Cocktail-Liste LiveData aus dem ViewModel und aktualisiert den RecyclerView-Adapter bei Änderungen.
        viewModel.cocktailsLiveData.observe(viewLifecycleOwner) { cocktails ->
            binding.cocktailsRV.adapter = CocktailAdapter(cocktails, viewModel)
        }
        viewModel.getCocktails() // Kokteyl listesini çekmek için ViewModel'deki fonksiyonu çağırır.
        // Ruft die Funktion im ViewModel auf, um die Cocktail-Liste zu holen.
    }

}

