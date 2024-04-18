package de.syntax.androidabschluss.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentNoteEditBinding
import de.syntax.androidabschluss.local.Note
import de.syntax.androidabschluss.utils.hideKeyBoard
import de.syntax.androidabschluss.viewmodel.MainViewModel


class NoteEditFragment : Fragment() {
    // ViewBinding ile UI bileşenlerine erişim sağlar.
    // Ermöglicht den Zugriff auf die UI-Komponenten über ViewBinding.
    private lateinit var binding: FragmentNoteEditBinding

    // Activity kapsamında ViewModel.
    // Greift auf das ViewModel innerhalb des Aktivitätskontextes zu.
    private val viewModel: MainViewModel by activityViewModels()

    // Düzenlenecek notun ID'si.
    // Die ID der zu bearbeitenden Notiz.
    private var noteId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fragment'a geçirilen argümanlardan not ID'si alınır.
        // Die Notiz-ID wird aus den an das Fragment übergebenen Argumenten abgerufen.
        arguments?.let {
            noteId = it.getLong("noteId")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Fragment layout'unun inflate işlemi ve binding nesnesinin başlatılması.
        // Initialisiert das Binding und bläht das Layout auf.
        binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility") // Android Lint'in bu özel uyarısını görmezden gelmesini sağlar. Böylece, dokunma olaylarını özelleştiren geliştiriciler, gereksiz uyarılardan kaçınabilir.
    // Ermöglicht es, dass Android Lint spezifische Warnungen ignoriert. Dies ist nützlich, wenn Entwickler benutzerdefinierte Touch-Events implementieren und unnötige Warnungen vermeiden möchten.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'den not listesi alınır ve düzenlenecek not bulunur.
        // Ruft die Notizenliste aus dem ViewModel ab und findet die zu bearbeitende Notiz.
        val note = viewModel.noteList.value?.find { it.id == noteId }

        // Eğer not bulunursa, UI bileşenleri notun mevcut bilgileri ile doldurulur.
        // Wenn die Notiz gefunden wird, werden die UI-Komponenten mit den aktuellen Informationen der Notiz gefüllt.
        if (note != null) {
            binding.editNameTextfield.setText(note.title)
            val index = resources.getStringArray(R.array.titel_array).indexOf(note.text)
            binding.editFoodSpinner.setSelection(index)
        }

        // Not kaydı tamamlandığında otomatik olarak not listesi ekranına dönme.
        // Kehrt automatisch zum Notizenlisten-Bildschirm zurück, wenn die Notizregistrierung abgeschlossen ist.
        viewModel.complete.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(NoteEditFragmentDirections.actionNoteEditFragmentToNoteFragment())
                viewModel.unsetComplete() // Tamamlama durumunu sıfırlama.
            }
        }

        // Spinner'a dokunulduğunda klavyeyi gizleme.
        // Verbirgt die Tastatur, wenn der Spinner berührt wird.
        binding.editFoodSpinner.setOnTouchListener { _, _ ->
            val imm: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            false
        }

        // "Save" butonuna tıklandığında notun güncellenmesi.
        // Aktualisiert die Notiz, wenn auf die Schaltfläche "Speichern" geklickt wird.
        binding.editSaveButton.setOnClickListener {
            if (note != null) {
                getValuesAndUpdate(note)
            }
        }

        // "Delete" butonuna tıklandığında notun silinmesi.
        // Löscht die Notiz, wenn auf die Schaltfläche "Löschen" geklickt wird.
        binding.editDeleteButton.setOnClickListener {
            if (note != null) {
                viewModel.deleteNote(note)
            }
        }

        // Hintergrund berühren, um die Tastatur zu verbergen.
        // Berühren Sie den Hintergrund, um die Tastatur zu verbergen.
        binding.editNoteHintergrund.setOnClickListener {
            view.context.hideKeyBoard(it)
        }
    }

    // UI'dan alınan yeni değerlerle notu güncelleme.
    // Aktualisiert die Notiz mit den neuen Werten aus der UI.
    private fun getValuesAndUpdate(note: Note) {
        note.title = binding.editNameTextfield.text.toString()
        note.text = binding.editFoodSpinner.selectedItem.toString()
        viewModel.updateNote(note)
    }
}