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
// Definiert einen RecyclerView-Adapter, der zum Anzeigen von Lieblingsrezepten verwendet wird.
class FavoriteRecipeAdapter(
    private var dataset: List<FavoriteMeal>, // Görüntülenecek favori yemeklerin listesi. // Liste der anzuzeigenden Lieblingsgerichte.
    private var viewModel: MainViewModel // Yemek detayları için ViewModel. // ViewModel für die Details der Gerichte.
) : RecyclerView.Adapter<FavoriteRecipeAdapter.FavoriteRecipeViewHolder>() { // RecyclerView.Adapter sınıfından türetilir. // Abgeleitet von der Klasse RecyclerView.Adapter.

    // Her bir liste öğesi için ViewHolder sınıfı.
    // ViewHolder-Klasse für jedes Listenelement.
    inner class FavoriteRecipeViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) // ViewHolder, yemek listesi öğeleri için binding nesnesini tutar. // ViewHolder hält das Binding-Objekt für die Gerichtelistenelemente.

    // ViewHolder oluşturulduğunda çağrılan metot. Öğe görünümleri burada şişirilir.
    // Methode, die aufgerufen wird, wenn der ViewHolder erstellt wird. Die Elementansichten werden hier aufgebläht.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false) // Özel layout kullanılarak binding nesnesi oluşturulur. // Das Binding-Objekt wird mit einem speziellen Layout erstellt.
        return FavoriteRecipeViewHolder(binding) // Oluşturulan binding ile ViewHolder döndürülür. // Der erstellte Binding wird mit dem ViewHolder zurückgegeben.
    }

    // DataSet'teki toplam öğe sayısını döndürür.
    // Gibt die Gesamtzahl der Elemente im DataSet zurück.
    override fun getItemCount(): Int {
        return dataset.size // Dataset listesindeki öğe sayısı kadarını döndürür. // Gibt die Anzahl der Elemente in der Dataset-Liste zurück.
    }

    // Verilen pozisyondaki veriyi ViewHolder'a bağlayan metot.
    // Methode, die die Daten an den ViewHolder an der gegebenen Position bindet.
    override fun onBindViewHolder(holder: FavoriteRecipeViewHolder, position: Int) {
        val item = dataset[position] // Pozisyondaki favori yemek alınır. // Das Lieblingsgericht an der Position wird geholt.
        holder.binding.productImageView.load(item.strMealThumb) // Yemeğin resmi yüklenir. // Das Bild des Gerichts wird geladen.
        holder.binding.productTextViewTitle.text = item.strMeal // Yemeğin adı TextView'a yazdırılır. // Der Name des Gerichts wird im TextView angezeigt.
        holder.binding.productTextViewTags.text = item.strCategory // Yemeğin kategorisi TextView'a yazdırılır. // Die Kategorie des Gerichts wird im TextView angezeigt.
        holder.binding.cvFavourites.setOnClickListener {
            // Favori yemeğin üzerine tıklandığında, yemeğin detay sayfasına navigasyon yapılır.
            // Wenn auf das Lieblingsgericht geklickt wird, erfolgt eine Navigation zur Detailseite des Gerichts.
            viewModel.getMealDetail(item.idMeal) // ViewModel aracılığıyla yemeğin detayı istenir. // Über das ViewModel werden die Details des Gerichts angefordert.
            holder.itemView.findNavController().navigate(R.id.recipeDetailFragment) // Detay sayfasına navigasyon yapılır. // Navigiert zur Detailseite.
        }
    }
}

