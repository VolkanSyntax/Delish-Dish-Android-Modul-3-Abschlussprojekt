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
import de.syntax.androidabschluss.viewmodel.MainViewModel


class NoteAddFragment : Fragment() {
    private lateinit var binding: FragmentNoteAddBinding // ViewBinding ile layout'a erişim.
    private val viewModel: MainViewModel by activityViewModels() // Activity kapsamında ViewModel.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Fragment layout'unun inflate edilmesi.
        binding = FragmentNoteAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Not kaydı tamamlandığında otomatik olarak önceki ekrana dönme.
        viewModel.complete.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(NoteAddFragmentDirections.actionNoteAddFragment2ToNoteFragment())
                viewModel.unsetComplete() // Tamamlama durumunu sıfırlama.
            }
        }

        // Spinner'a dokunulduğunda klavyeyi gizleme.
        binding.foodSpinner.setOnTouchListener { _, _ ->
            val imm: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            false
        }

        // "Save" butonuna basıldığında notu kaydetme.
        binding.saveButton.setOnClickListener {
            getValuesAndSave()
        }

        // "Cancel" butonuna basıldığında önceki ekrana dönme.
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(NoteAddFragmentDirections.actionNoteAddFragment2ToNoteFragment())
        }
    }

    // EditText ve Spinner'dan alınan değerlerle yeni bir not oluşturup kaydetme.
    private fun getValuesAndSave() {
        val title = binding.nameEdit.text.toString()
        val text = binding.foodSpinner.selectedItem.toString()
        val newNote = Note(title = title, text = text)
        viewModel.insertNote(newNote) // ViewModel aracılığıyla notu kaydetme.
    }
}
