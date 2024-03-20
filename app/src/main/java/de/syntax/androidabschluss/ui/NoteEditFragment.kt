package de.syntax.androidabschluss.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentNoteEditBinding
import de.syntax.androidabschluss.local.Note
import de.syntax.androidabschluss.viewmodel.MainViewModel


class NoteEditFragment : Fragment() {
    private lateinit var binding: FragmentNoteEditBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var noteId: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            noteId = it.getLong("noteId")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteEditBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val note = viewModel.noteList.value?.find { it.id == noteId }

        if (note != null){
            binding.editNameEdit.setText(note.title)
            val index = resources.getStringArray(R.array.titel_array).indexOf(note.text)
            binding.editFoodSpinner.setSelection(index)
        }

        viewModel.complete.observe(viewLifecycleOwner) {
            if (it){
                findNavController().navigate(NoteEditFragmentDirections.actionNoteEditFragmentToNoteFragment())
                viewModel.unsetComplete()
            }
        }

        binding.editSaveButton.setOnClickListener {
            if (note != null) {
                getValuesAndUpdate(note)
            }
        }

        binding.editDeleteButton.setOnClickListener {
            if (note != null) {
                viewModel.deleteNote(note)
            }
        }

    }

    private fun getValuesAndUpdate(note: Note) {
        note.title = binding.editNameEdit.text.toString()
        note.text = binding.editFoodSpinner.selectedItem.toString()
        viewModel.updateNote(note)
    }

}