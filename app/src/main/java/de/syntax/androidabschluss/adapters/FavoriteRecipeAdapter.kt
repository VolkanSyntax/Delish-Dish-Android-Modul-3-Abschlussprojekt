package de.syntax.androidabschluss.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.ItemFavoriteBinding
import de.syntax.androidabschluss.local.FavoriteMeal
import de.syntax.androidabschluss.viewmodel.MainViewModel

// Favori yemek tariflerini göstermek için kullanılan RecyclerView Adapter'ı tanımlar.
class FavoriteRecipeAdapter(
    private var dataset: List<FavoriteMeal>, // Görüntülenecek favori yemeklerin listesi.
    private var viewModel: MainViewModel // Yemek detayları için ViewModel.
) : RecyclerView.Adapter<FavoriteRecipeAdapter.FavoriteRecipeViewHolder>() { // RecyclerView.Adapter sınıfından türetilir.

    // Her bir liste öğesi için ViewHolder sınıfı.
    inner class FavoriteRecipeViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) // ViewHolder, yemek listesi öğeleri için binding nesnesini tutar.

    // ViewHolder oluşturulduğunda çağrılan metot. Öğe görünümleri burada şişirilir.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false) // Özel layout kullanılarak binding nesnesi oluşturulur.
        return FavoriteRecipeViewHolder(binding) // Oluşturulan binding ile ViewHolder döndürülür.
    }

    // DataSet'teki toplam öğe sayısını döndürür.
    override fun getItemCount(): Int {
        return dataset.size // Dataset listesindeki öğe sayısı kadarını döndürür.
    }

    // Verilen pozisyondaki veriyi ViewHolder'a bağlayan metot.
    override fun onBindViewHolder(holder: FavoriteRecipeViewHolder, position: Int) {
        val item = dataset[position] // Pozisyondaki favori yemek alınır.
        holder.binding.productImageView.load(item.strMealThumb) // Yemeğin resmi yüklenir.
        holder.binding.productTextViewTitle.text = item.strMeal // Yemeğin adı TextView'a yazdırılır.
        holder.binding.productTextViewTags.text = item.strCategory // Yemeğin kategorisi TextView'a yazdırılır.
        holder.binding.cvFavourites.setOnClickListener {
            // Favori yemeğin üzerine tıklandığında, yemeğin detay sayfasına navigasyon yapılır.
            viewModel.getMealDetail(item.idMeal) // ViewModel aracılığıyla yemeğin detayı istenir.
            holder.itemView.findNavController().navigate(R.id.recipeDetailFragment) // Detay sayfasına navigasyon yapılır.
        }
    }
}
