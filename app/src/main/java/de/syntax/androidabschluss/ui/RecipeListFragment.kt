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
import de.syntax.androidabschluss.utils.hideKeyBoard
import de.syntax.androidabschluss.viewmodel.MainViewModel

class RecipeListFragment : Fragment() {
    // ViewModel, bu Fragment'ta kullanılan verileri yönetir.
    // ViewModel verwaltet die Daten, die in diesem Fragment verwendet werden.
    private val viewModel: MainViewModel by activityViewModels()

    // View Binding nesnesi, bu Fragment'a ait view'lara erişim sağlar.
    // View Binding-Objekt ermöglicht den Zugriff auf die Ansichten dieses Fragments.
    private lateinit var binding: FragmentRecipeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Fragment'in layout'u inflate ediliyor ve binding nesnesi ile bağlanıyor.
        // Das Layout des Fragments wird aufgeblasen und mit dem Binding-Objekt verbunden.
        binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Arama butonuna basıldığında, ViewModel'deki arama fonksiyonu çağrılıyor.
        // Wenn die Suchschaltfläche gedrückt wird, wird die Suchfunktion im ViewModel aufgerufen.
        binding.recipeListSearchButton.setOnClickListener {
            val searchValue = binding.recipeListEditTextSearch.text.toString()
            viewModel.searchMeals(searchValue)
            view.context.hideKeyBoard(it)
        }

        // Yardım butonuna tıklanıldığında, kullanıcı yardım ekranına yönlendiriliyor.
        // Wenn die Hilfeschaltfläche geklickt wird, wird der Benutzer zur Hilfeseite weitergeleitet.
        binding.assistant.setOnClickListener {
            findNavController().navigate(R.id.assistantFragment)
        }

        // RecyclerView için GridLayoutManager ayarlanıyor.
        // Für RecyclerView wird ein GridLayoutManager eingestellt.
        binding.reciperListRV.layoutManager = GridLayoutManager(context, 2)

        // RecyclerView için sabit boyut ayarı yapılıyor.
        // Für RecyclerView wird eine feste Größeneinstellung vorgenommen.
        binding.reciperListRV.hasFixedSize()

        // ViewModel'den alınan yemek listesi LiveData'sı gözlemleniyor.
        // Die Lebensmittelliste LiveData aus dem ViewModel wird beobachtet.
        viewModel.mealsLiveData.observe(viewLifecycleOwner) { meals ->
            // Alınan yemek listesine göre RecyclerView adapter'ı güncelleniyor.
            // Der RecyclerView-Adapter wird entsprechend der erhaltenen Lebensmittelliste aktualisiert.
            binding.reciperListRV.adapter = RecipeAdapter(meals, viewModel)
        }

        // Fragment yüklendiğinde, yemek listesi ViewModel üzerinden çekiliyor.
        // Beim Laden des Fragments wird die Lebensmittelliste über das ViewModel abgerufen.
        viewModel.getMeals()
    }
}

