package de.syntax.androidabschluss.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentNoteAddBinding
import de.syntax.androidabschluss.local.Note
import de.syntax.androidabschluss.viewmodel.MainViewModel


class NoteAddFragment : Fragment() {
    private lateinit var binding: FragmentNoteAddBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteAddBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.complete.observe(viewLifecycleOwner){
            if (it) {
                findNavController().navigate(NoteAddFragmentDirections.actionNoteAddFragment2ToNoteFragment())
                viewModel.unsetComplete()
            }
        }


        binding.foodSpinner.setOnTouchListener { view, motionEvent ->  val imm: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        binding.saveButton.setOnClickListener {
            getValuesAndSave()
        }

        binding.cancelButton.setOnClickListener {
            findNavController().navigate(NoteAddFragmentDirections.actionNoteAddFragment2ToNoteFragment())
        }


    }


    private fun getValuesAndSave() {
        val title = binding.nameEdit.text.toString()
        val text = binding.foodSpinner.selectedItem.toString()
        val newNote = Note(title = title, text = text)
        viewModel.insertNote(newNote)
    }



}