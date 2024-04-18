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
// Definiert eine RecyclerView-Adapterklasse für eine Liste von Lieblingscocktails.
class FavoriteCocktailAdapter(
    private val dataset: List<FavoriteCocktail>, // Görüntülenecek favori kokteyllerin listesi. // Liste der anzuzeigenden Lieblingscocktails.
    private val viewModel: MainViewModel // Kokteyl detayları için ViewModel. // ViewModel für Cocktaildetails.
) : RecyclerView.Adapter<FavoriteCocktailAdapter.FavoriteCocktailViewHolder>() { // RecyclerView.Adapter sınıfından türetilir. // Abgeleitet von der Klasse RecyclerView.Adapter.

    // Her bir liste öğesi için ViewHolder sınıfı.
    // ViewHolder-Klasse für jedes Listenelement.
    inner class FavoriteCocktailViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) // ViewHolder, kokteyl listesi öğeleri için binding nesnesini tutar. // ViewHolder hält das Binding-Objekt für die Cocktail-Listenelemente.

    // ViewHolder oluşturulduğunda çağrılan metot. Öğe görünümleri burada şişirilir.
    // Methode, die aufgerufen wird, wenn der ViewHolder erstellt wird. Die Elementansichten werden hier aufgebläht.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCocktailViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false) // Özel layout kullanılarak binding nesnesi oluşturulur. // Das Binding-Objekt wird mit einem speziellen Layout erstellt.
        return FavoriteCocktailViewHolder(binding) // Oluşturulan binding ile ViewHolder döndürülür. // Der erstellte Binding wird mit dem ViewHolder zurückgegeben.
    }

    // DataSet'teki toplam öğe sayısını döndürür.
    // Gibt die Gesamtzahl der Elemente im DataSet zurück.
    override fun getItemCount(): Int {
        return dataset.size // Dataset listesindeki öğe sayısı kadarını döndürür. // Gibt die Anzahl der Elemente in der Dataset-Liste zurück.
    }

    // Verilen pozisyondaki veriyi ViewHolder'a bağlayan metot.
    // Methode, die die Daten an den ViewHolder an der gegebenen Position bindet.
    override fun onBindViewHolder(holder: FavoriteCocktailViewHolder, position: Int) {
        val item = dataset[position] // Pozisyondaki favori kokteyl alınır. // Der Lieblingscocktail an der Position wird geholt.
        holder.binding.productImageView.load(item.strDrinkThumb) // Kokteylin resmi yüklenir. // Das Bild des Cocktails wird geladen.
        holder.binding.productTextViewTitle.text = item.strDrink // Kokteylin adı TextView'a yazdırılır. // Der Name des Cocktails wird im TextView angezeigt.
        holder.binding.productTextViewTags.text = item.strCategory // Kokteylin kategorisi TextView'a yazdırılır. // Die Kategorie des Cocktails wird im TextView angezeigt.
        holder.binding.cvFavourites.setOnClickListener {
            // Favori kokteylin üzerine tıklandığında, kokteylin detay sayfasına navigasyon yapılır.
            // Wenn auf den Lieblingscocktail geklickt wird, erfolgt eine Navigation zur Detailseite des Cocktails.
            viewModel.getCocktailDetail(item.idDrink) // ViewModel aracılığıyla kokteylin detayı istenir. // Über das ViewModel werden die Details des Cocktails angefordert.
            holder.itemView.findNavController().navigate(R.id.cocktailDetailFragment) // Detay sayfasına navigasyon yapılır. // Navigiert zur Detailseite.
        }
    }
}

