package de.syntax.androidabschluss.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.databinding.ItemRecipeBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel

// Yemek tariflerini listelemek için kullanılan RecyclerView adaptörü.
class RecipeAdapter(
    private val dataset: List<Meal>, // Görüntülenecek yemeklerin listesi.
    private val viewModel: MainViewModel // Yemek detayları için ViewModel.
): RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() { // RecyclerView.Adapter sınıfından türetilir.

    // Her bir liste öğesi için ViewHolder sınıfı.
    inner class RecipeViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    // ViewHolder oluşturulduğunda çağrılan metot. Öğe görünümleri burada şişirilir.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding) // Oluşturulan binding ile ViewHolder döndürülür.
    }

    // DataSet'teki toplam öğe sayısını döndürür.
    override fun getItemCount(): Int {
        return dataset.size // Dataset listesindeki öğe sayısı kadarını döndürür.
    }

    // Verilen pozisyondaki veriyi ViewHolder'a bağlayan metot.
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = dataset[position] // Pozisyondaki yemek alınır.
        holder.binding.productImageView.load(item.strMealThumb) // Yemeğin resmi yüklenir.
        holder.binding.productTextViewTitle.text = item.strMeal // Yemeğin adı TextView'a yazdırılır.
        holder.binding.productTextViewTags.text = item.strCategory // Yemeğin kategorisi TextView'a yazdırılır.

        // Yemek öğesine tıklanıldığında yemeğin detay sayfasına yönlendirir.
        holder.binding.productrecipeCardView.setOnClickListener {
            viewModel.getMealDetail(item.idMeal) // ViewModel aracılığıyla yemeğin detayı istenir.
            holder.itemView.findNavController().navigate(R.id.recipeDetailFragment) // Detay sayfasına navigasyon yapılır.
        }
    }
}
