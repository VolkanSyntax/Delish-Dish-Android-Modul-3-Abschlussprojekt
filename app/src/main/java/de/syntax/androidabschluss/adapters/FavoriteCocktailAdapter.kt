package de.syntax.androidabschluss.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.ItemFavoriteBinding
import de.syntax.androidabschluss.local.FavoriteCocktail
import de.syntax.androidabschluss.viewmodel.MainViewModel

// Favori kokteyller listesi için bir RecyclerView Adapter sınıfı tanımlar.
class FavoriteCocktailAdapter(
    private val dataset: List<FavoriteCocktail>, // Görüntülenecek favori kokteyllerin listesi.
    private val viewModel: MainViewModel // Kokteyl detayları için ViewModel.
) : RecyclerView.Adapter<FavoriteCocktailAdapter.FavoriteCocktailViewHolder>() { // RecyclerView.Adapter sınıfından türetilir.

    // Her bir liste öğesi için ViewHolder sınıfı.
    inner class FavoriteCocktailViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) // ViewHolder, kokteyl listesi öğeleri için binding nesnesini tutar.

    // ViewHolder oluşturulduğunda çağrılan metot. Öğe görünümleri burada şişirilir.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCocktailViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false) // Özel layout kullanılarak binding nesnesi oluşturulur.
        return FavoriteCocktailViewHolder(binding) // Oluşturulan binding ile ViewHolder döndürülür.
    }

    // DataSet'teki toplam öğe sayısını döndürür.
    override fun getItemCount(): Int {
        return dataset.size // Dataset listesindeki öğe sayısı kadarını döndürür.
    }

    // Verilen pozisyondaki veriyi ViewHolder'a bağlayan metot.
    override fun onBindViewHolder(holder: FavoriteCocktailViewHolder, position: Int) {
        val item = dataset[position] // Pozisyondaki favori kokteyl alınır.
        holder.binding.productImageView.load(item.strDrinkThumb) // Kokteylin resmi yüklenir.
        holder.binding.productTextViewTitle.text = item.strDrink // Kokteylin adı TextView'a yazdırılır.
        holder.binding.productTextViewTags.text = item.strCategory // Kokteylin kategorisi TextView'a yazdırılır.
        holder.binding.cvFavourites.setOnClickListener {
            // Favori kokteylin üzerine tıklandığında, kokteylin detay sayfasına navigasyon yapılır.
            viewModel.getCocktailDetail(item.idDrink) // ViewModel aracılığıyla kokteylin detayı istenir.
            holder.itemView.findNavController().navigate(R.id.cocktailDetailFragment) // Detay sayfasına navigasyon yapılır.
        }
    }
}

