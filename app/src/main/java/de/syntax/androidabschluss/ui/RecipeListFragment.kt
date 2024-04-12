package de.syntax.androidabschluss.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.adapters.RecipeAdapter
import de.syntax.androidabschluss.databinding.FragmentRecipeListBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel

class RecipeListFragment : Fragment() {
    // ViewModel, bu Fragment'ta kullanılan verileri yönetir.
    private val viewModel: MainViewModel by activityViewModels()
    // View Binding nesnesi, bu Fragment'a ait view'lara erişim sağlar.
    private lateinit var binding: FragmentRecipeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Fragment'in layout'u inflate ediliyor ve binding nesnesi ile bağlanıyor.
        binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Arama butonuna basıldığında, ViewModel'deki arama fonksiyonu çağrılıyor.
        binding.recipeListSearchButton.setOnClickListener {
            val searchValue = binding.recipeListEditTextSearch.text.toString()
            viewModel.searchMeals(searchValue)
            hideKeyboard(view)
        }

        // Yardım butonuna tıklanıldığında, kullanıcı yardım ekranına yönlendiriliyor.
        binding.assistant.setOnClickListener {
            findNavController().navigate(R.id.assistantFragment)
        }

        // RecyclerView için GridLayoutManager ayarlanıyor.
        binding.reciperListRV.layoutManager = GridLayoutManager(context,2)

        // RecyclerView için sabit boyut ayarı yapılıyor.
        binding.reciperListRV.hasFixedSize()

        // ViewModel'den alınan yemek listesi LiveData'sı gözlemleniyor.
        viewModel.mealsLiveData.observe(viewLifecycleOwner) { meals ->
            // Alınan yemek listesine göre RecyclerView adapter'ı güncelleniyor.
            binding.reciperListRV.adapter = RecipeAdapter(meals, viewModel)
        }

        // Fragment yüklendiğinde, yemek listesi ViewModel üzerinden çekiliyor.
        viewModel.getMeals()
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
