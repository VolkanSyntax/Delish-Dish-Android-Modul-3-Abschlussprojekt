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
// Adapterklasse, die verwendet wird, um Cocktails in einem RecyclerView anzuzeigen.
class CocktailAdapter(
    private val dataset: List<Cocktail>, // Görüntülenecek kokteyllerin listesi. // Liste der anzuzeigenden Cocktails.
    private val viewModel: MainViewModel // Verileri yönetmek için ViewModel. // ViewModel zur Datenverwaltung.
): RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() { // RecyclerView.Adapter alt sınıfı. // Unterklasse von RecyclerView.Adapter.

    // Her bir liste öğesi için ViewHolder sınıfı.
    // ViewHolder-Klasse für jedes Listenelement.
    inner class CocktailViewHolder(val binding: ItemCocktailBinding): RecyclerView.ViewHolder(binding.root)

    // ViewHolder oluşturulduğunda çağrılır; layout şişirilir ve ViewHolder'a bağlanır.
    // Wird aufgerufen, wenn der ViewHolder erstellt wird; das Layout wird aufgebläht und an den ViewHolder gebunden.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(binding)
    }

    // DataSet içindeki toplam öğe sayısını döndürür.
    // Gibt die Gesamtanzahl der Elemente im DataSet zurück.
    override fun getItemCount(): Int {
        return dataset.size
    }

    // Verilen pozisyondaki verileri ViewHolder'a bağlar.
    // Bindet die Daten an den ViewHolder an der gegebenen Position.
    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val item = dataset[position] // Pozisyondaki kokteyl. // Cocktail an der Position.

        holder.binding.productImageView.load(item.strDrinkThumb) // Kokteylin resmini yükler. // Lädt das Bild des Cocktails.
        holder.binding.productTextViewTitle.text = item.strDrink // Kokteylin adını ayarlar. // Stellt den Namen des Cocktails ein.
        holder.binding.productTextViewTags.text = item.strCategory // Kokteylin kategorisini ayarlar. // Stellt die Kategorie des Cocktails ein.

        // Kokteylin tıklandığında detay sayfasına navigasyon yapar.
        // Navigiert zur Detailseite, wenn auf den Cocktail geklickt wird.
        holder.binding.productcocktailCardView.setOnClickListener {
            viewModel.getCocktailDetail(item.idDrink) // Kokteylin detayını getirir. // Ruft die Details des Cocktails ab.
            holder.itemView.findNavController().navigate(R.id.cocktailDetailFragment) // Detay sayfasına gider. // Navigiert zur Detailseite.
        }
    }
}
