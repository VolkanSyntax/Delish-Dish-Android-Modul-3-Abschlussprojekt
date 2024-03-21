package de.syntax.androidabschluss.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.adapters.NoteAdapter
import de.syntax.androidabschluss.databinding.FragmentNoteBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel


class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.noteList.observe(viewLifecycleOwner) { notes ->
            binding.notelist.adapter = NoteAdapter(notes, viewModel)


            binding.addNoteButton.setOnClickListener {
                findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToNoteAddFragment2())
            }


        }
    }
}