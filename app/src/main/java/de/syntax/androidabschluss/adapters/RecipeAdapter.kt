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
// RecyclerView-Adapter, der zum Auflisten von Rezepten verwendet wird.
class RecipeAdapter(
    private val dataset: List<Meal>, // Görüntülenecek yemeklerin listesi. // Liste der anzuzeigenden Gerichte.
    private val viewModel: MainViewModel // Yemek detayları için ViewModel. // ViewModel für die Details der Gerichte.
): RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() { // RecyclerView.Adapter sınıfından türetilir. // Abgeleitet von der Klasse RecyclerView.Adapter.

    // Her bir liste öğesi için ViewHolder sınıfı.
    // ViewHolder-Klasse für jedes Listenelement.
    inner class RecipeViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    // ViewHolder oluşturulduğunda çağrılan metot. Öğe görünümleri burada şişirilir.
    // Methode, die aufgerufen wird, wenn der ViewHolder erstellt wird. Die Elementansichten werden hier aufgebläht.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding) // Oluşturulan binding ile ViewHolder döndürülür. // Der erstellte Binding wird mit dem ViewHolder zurückgegeben.
    }

    // DataSet'teki toplam öğe sayısını döndürür.
    // Gibt die Gesamtzahl der Elemente im DataSet zurück.
    override fun getItemCount(): Int {
        return dataset.size // Dataset listesindeki öğe sayısı kadarını döndürür. // Gibt die Anzahl der Elemente in der Dataset-Liste zurück.
    }

    // Verilen pozisyondaki veriyi ViewHolder'a bağlayan metot.
    // Methode, die die Daten an den ViewHolder an der gegebenen Position bindet.
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = dataset[position] // Pozisyondaki yemek alınır. // Das Gericht an der Position wird geholt.
        holder.binding.productImageView.load(item.strMealThumb) // Yemeğin resmi yüklenir. // Das Bild des Gerichts wird geladen.
        holder.binding.productTextViewTitle.text = item.strMeal // Yemeğin adı TextView'a yazdırılır. // Der Name des Gerichts wird im TextView angezeigt.
        holder.binding.productTextViewTags.text = item.strCategory // Yemeğin kategorisi TextView'a yazdırılır. // Die Kategorie des Gerichts wird im TextView angezeigt.

        // Yemek öğesine tıklanıldığında yemeğin detay sayfasına yönlendirir.
        // Leitet zur Detailseite des Gerichts weiter, wenn auf das Gerichtelement geklickt wird.
        holder.binding.productrecipeCardView.setOnClickListener {
            viewModel.getMealDetail(item.idMeal) // ViewModel aracılığıyla yemeğin detayı istenir. // Über das ViewModel werden die Details des Gerichts angefordert.
            holder.itemView.findNavController().navigate(R.id.recipeDetailFragment) // Detay sayfasına navigasyon yapılır. // Navigiert zur Detailseite.
        }
    }
}
