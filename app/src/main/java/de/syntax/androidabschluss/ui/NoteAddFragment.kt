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
import de.syntax.androidabschluss.databinding.FragmentNoteAddBinding
import de.syntax.androidabschluss.local.Note
import de.syntax.androidabschluss.utils.hideKeyBoard
import de.syntax.androidabschluss.viewmodel.MainViewModel


class NoteAddFragment : Fragment() {
    private lateinit var binding: FragmentNoteAddBinding // ViewBinding ile layout'a erişim.
    // Zugriff auf das Layout über ViewBinding.
    private val viewModel: MainViewModel by activityViewModels() // Activity kapsamında ViewModel.
    // ViewModel im Kontext der Activity.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Fragment layout'unun inflate edilmesi.
        // Inflation des Fragment-Layouts.
        binding = FragmentNoteAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility") // Android Lint'in bu özel uyarısını görmezden gelmesini sağlar. Böylece, dokunma olaylarını özelleştiren geliştiriciler, gereksiz uyarılardan kaçınabilir.
                                               // Ermöglicht es, dass Android Lint spezifische Warnungen ignoriert. Dies ist nützlich, wenn Entwickler benutzerdefinierte Touch-Events implementieren und unnötige Warnungen vermeiden möchten.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Not kaydı tamamlandığında otomatik olarak önceki ekrana dönme.
        // Automatische Rückkehr zum vorherigen Bildschirm nach Abschluss der Notizregistrierung.
        viewModel.complete.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(NoteAddFragmentDirections.actionNoteAddFragment2ToNoteFragment())
                viewModel.unsetComplete() // Tamamlama durumunu sıfırlama.
                // Zurücksetzen des Abschlussstatus.
            }
        }

        // Spinner'a dokunulduğunda klavyeyi gizleme.
        // Tastatur ausblenden, wenn der Spinner berührt wird.
        binding.addFoodSpinner.setOnTouchListener { _, _ ->
            val imm: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            false
        }

        // "Save" butonuna basıldığında notu kaydetme.
        // Speichern der Notiz beim Drücken der "Speichern"-Schaltfläche.
        binding.saveButton.setOnClickListener {
            getValuesAndSave()
        }

        // "Cancel" butonuna basıldığında önceki ekrana dönme.
        // Zurück zum vorherigen Bildschirm beim Drücken der "Abbrechen"-Schaltfläche.
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(NoteAddFragmentDirections.actionNoteAddFragment2ToNoteFragment())
        }

        // Ekrana dokunulduğunda klavyeyi gizleme işlemi.
        // Tastatur ausblenden, wenn auf den Bildschirm getippt wird.
        binding.addNoteHintergrund.setOnClickListener {
            view.context.hideKeyBoard(it)
        }
    }

    // EditText ve Spinner'dan alınan değerlerle yeni bir not oluşturup kaydetme.
    // Erstellen und Speichern einer neuen Notiz mit den Werten aus EditText und Spinner.
    private fun getValuesAndSave() {
        val title = binding.nameEdit.text.toString()
        val text = binding.addFoodSpinner.selectedItem.toString()
        val newNote = Note(title = title, text = text)
        viewModel.insertNote(newNote) // ViewModel aracılığıyla notu kaydetme.
        // Speichern der Notiz über das ViewModel.
    }

}
