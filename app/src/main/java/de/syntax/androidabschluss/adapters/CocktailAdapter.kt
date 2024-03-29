package de.syntax.androidabschluss.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.databinding.ItemCocktailBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel

// Kokteylleri bir RecyclerView içinde göstermek için kullanılan Adapter sınıfı.
class CocktailAdapter(
    private val dataset: List<Cocktail>, // Görüntülenecek kokteyllerin listesi.
    private val viewModel: MainViewModel // Verileri yönetmek için ViewModel.
): RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() { // RecyclerView.Adapter alt sınıfı.

    // Her bir liste öğesi için ViewHolder sınıfı.
    inner class CocktailViewHolder(val binding: ItemCocktailBinding): RecyclerView.ViewHolder(binding.root)

    // ViewHolder oluşturulduğunda çağrılır; layout şişirilir ve ViewHolder'a bağlanır.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CocktailViewHolder(binding)
    }

    // DataSet içindeki toplam öğe sayısını döndürür.
    override fun getItemCount(): Int {
        return dataset.size
    }

    // Verilen pozisyondaki verileri ViewHolder'a bağlar.
    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val item = dataset[position] // Pozisyondaki kokteyl.

        holder.binding.productImageView.load(item.strDrinkThumb) // Kokteylin resmini yükler.
        holder.binding.productTextViewTitle.text = item.strDrink // Kokteylin adını ayarlar.
        holder.binding.productTextViewTags.text = item.strCategory // Kokteylin kategorisini ayarlar.

        // Kokteylin tıklandığında detay sayfasına navigasyon yapar.
        holder.binding.productcocktailCardView.setOnClickListener {
            viewModel.getCocktailDetail(item.idDrink) // Kokteylin detayını getirir.
            holder.itemView.findNavController().navigate(R.id.cocktailDetailFragment) // Detay sayfasına gider.
        }
    }
}