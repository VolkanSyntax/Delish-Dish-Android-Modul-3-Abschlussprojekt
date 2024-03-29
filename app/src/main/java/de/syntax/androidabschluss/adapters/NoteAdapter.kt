package de.syntax.androidabschluss.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.databinding.ItemNoteBinding
import de.syntax.androidabschluss.local.Note
import de.syntax.androidabschluss.ui.NoteFragmentDirections
import de.syntax.androidabschluss.viewmodel.MainViewModel

// Notların listesini göstermek için RecyclerView Adapter'ı tanımlar.
class NoteAdapter(
    private val dataset: List<Note>, // Görüntülenecek notların listesi.
    private val viewModel: MainViewModel, // Not işlemleri için ViewModel.
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() { // RecyclerView.Adapter sınıfından türetilir.

    // Her bir liste öğesi için ViewHolder sınıfı.
    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) // ViewHolder, not listesi öğeleri için binding nesnesini tutar.

    // ViewHolder oluşturulduğunda çağrılan metot. Öğe görünümleri burada şişirilir.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false) // Özel layout kullanılarak binding nesnesi oluşturulur.
        return NoteViewHolder(binding) // Oluşturulan binding ile ViewHolder döndürülür.
    }

    // DataSet'teki toplam öğe sayısını döndürür.
    override fun getItemCount(): Int {
        return dataset.size // Dataset listesindeki öğe sayısı kadarını döndürür.
    }

    // Verilen pozisyondaki veriyi ViewHolder'a bağlayan metot.
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = dataset[position] // Pozisyondaki not alınır.
        holder.binding.itemNameText.text = note.title // Notun başlığı TextView'a yazdırılır.
        holder.binding.itemFoodText.text = note.text // Notun içeriği TextView'a yazdırılır.

        // Silme ikonuna tıklanıldığında notun silinmesi için ViewModel metodunu çağırır.
        holder.binding.deleteCardIcon.setOnClickListener {
            viewModel.deleteNote(note) // ViewModel aracılığıyla notun silinmesi istenir.
        }

        // Not öğesine tıklanıldığında notun düzenleme sayfasına yönlendirir.
        holder.binding.itemLayout.setOnClickListener {
            val navController = holder.binding.itemLayout.findNavController() // NavController nesnesi alınır.
            // NoteFragment'tan NoteEditFragment'a navigasyon yapılır ve notun id'si argüman olarak geçilir.
            navController.navigate(NoteFragmentDirections.actionNoteFragmentToNoteEditFragment(note.id))
        }
    }
}
