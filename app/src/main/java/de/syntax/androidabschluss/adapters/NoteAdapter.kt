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
// Definiert einen RecyclerView-Adapter zum Anzeigen einer Liste von Notizen.
class NoteAdapter(
    private val dataset: List<Note>, // Görüntülenecek notların listesi. // Liste der anzuzeigenden Notizen.
    private val viewModel: MainViewModel, // Not işlemleri için ViewModel. // ViewModel für Notizoperationen.
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() { // RecyclerView.Adapter sınıfından türetilir. // Abgeleitet von der Klasse RecyclerView.Adapter.

    // Her bir liste öğesi için ViewHolder sınıfı.
    // ViewHolder-Klasse für jedes Listenelement.
    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) // ViewHolder, not listesi öğeleri için binding nesnesini tutar. // ViewHolder hält das Binding-Objekt für die Notizenlistenelemente.

    // ViewHolder oluşturulduğunda çağrılan metot. Öğe görünümleri burada şişirilir.
    // Methode, die aufgerufen wird, wenn der ViewHolder erstellt wird. Die Elementansichten werden hier aufgebläht.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false) // Özel layout kullanılarak binding nesnesi oluşturulur. // Das Binding-Objekt wird mit einem speziellen Layout erstellt.
        return NoteViewHolder(binding) // Oluşturulan binding ile ViewHolder döndürülür. // Der erstellte Binding wird mit dem ViewHolder zurückgegeben.
    }

    // DataSet'teki toplam öğe sayısını döndürür.
    // Gibt die Gesamtzahl der Elemente im DataSet zurück.
    override fun getItemCount(): Int {
        return dataset.size // Dataset listesindeki öğe sayısı kadarını döndürür. // Gibt die Anzahl der Elemente in der Dataset-Liste zurück.
    }

    // Verilen pozisyondaki veriyi ViewHolder'a bağlayan metot.
    // Methode, die die Daten an den ViewHolder an der gegebenen Position bindet.
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = dataset[position] // Pozisyondaki not alınır. // Die Notiz an der Position wird geholt.
        holder.binding.itemNameText.text = note.title // Notun başlığı TextView'a yazdırılır. // Der Titel der Notiz wird im TextView angezeigt.
        holder.binding.itemFoodText.text = note.text // Notun içeriği TextView'a yazdırılır. // Der Inhalt der Notiz wird im TextView angezeigt.

        // Silme ikonuna tıklanıldığında notun silinmesi için ViewModel metodunu çağırır.
        // Wenn auf das Löschsymbol geklickt wird, wird die Methode im ViewModel aufgerufen, um die Notiz zu löschen.
        holder.binding.deleteCardIcon.setOnClickListener {
            viewModel.deleteNote(note) // ViewModel aracılığıyla notun silinmesi istenir. // Über das ViewModel wird das Löschen der Notiz angefordert.
        }

        // Not öğesine tıklanıldığında notun düzenleme sayfasına yönlendirir.
        // Leitet zur Bearbeitungsseite der Notiz weiter, wenn auf das Notizelement geklickt wird.
        holder.binding.itemLayout.setOnClickListener {
            val navController = holder.binding.itemLayout.findNavController() // NavController nesnesi alınır. // Der NavController wird geholt.
            // NoteFragment'tan NoteEditFragment'a navigasyon yapılır ve notun id'si argüman olarak geçilir.
            // Navigiert vom NoteFragment zum NoteEditFragment und übergibt die ID der Notiz als Argument.
            navController.navigate(NoteFragmentDirections.actionNoteFragmentToNoteEditFragment(note.id))
        }
    }
}
