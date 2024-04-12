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
    private lateinit var binding: FragmentNoteEditBinding // ViewBinding ile UI bileşenlerine erişim sağlar.
    private val viewModel: MainViewModel by activityViewModels() // Activity kapsamında ViewModel.
    private var noteId: Long = 0 // Düzenlenecek notun ID'si.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fragment'a geçirilen argümanlardan not ID'si alınır.
        arguments?.let {
            noteId = it.getLong("noteId")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Fragment layout'unun inflate işlemi ve binding nesnesinin başlatılması.
        binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'den not listesi alınır ve düzenlenecek not bulunur.
        val note = viewModel.noteList.value?.find { it.id == noteId }

        // Eğer not bulunursa, UI bileşenleri notun mevcut bilgileri ile doldurulur.
        if (note != null) {
            binding.editNameTextfield.setText(note.title)
            val index = resources.getStringArray(R.array.titel_array).indexOf(note.text)
            binding.editFoodSpinner.setSelection(index)
        }



        // Not kaydı tamamlandığında otomatik olarak not listesi ekranına dönme.
        viewModel.complete.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(NoteEditFragmentDirections.actionNoteEditFragmentToNoteFragment())
                viewModel.unsetComplete() // Tamamlama durumunu sıfırlama.
            }
        }

        // Spinner'a dokunulduğunda klavyeyi gizleme.
        binding.editFoodSpinner.setOnTouchListener { _, _ ->
            val imm: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            false
        }



        // "Save" butonuna tıklandığında notun güncellenmesi.
        binding.editSaveButton.setOnClickListener {
            if (note != null) {
                getValuesAndUpdate(note)
            }
        }

        // "Delete" butonuna tıklandığında notun silinmesi.
        binding.editDeleteButton.setOnClickListener {
            if (note != null) {
                viewModel.deleteNote(note)
            }
        }

        binding.editNoteHintergrund.setOnClickListener {
           view.context.hideKeyBoard(it)  // klavye gizleme temiz kod (>.<)
        }
    }

    // UI'dan alınan yeni değerlerle notu güncelleme.
    private fun getValuesAndUpdate(note: Note) {
        note.title = binding.editNameTextfield.text.toString()
        note.text = binding.editFoodSpinner.selectedItem.toString()
        viewModel.updateNote(note)
    }


}
