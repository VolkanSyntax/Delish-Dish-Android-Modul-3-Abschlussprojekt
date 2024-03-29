package de.syntax.androidabschluss.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.adapters.NoteAdapter
import de.syntax.androidabschluss.databinding.FragmentNoteBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel


class NoteFragment : Fragment() {
    // ViewBinding ile UI bileşenlerine erişim sağlar.
    private lateinit var binding: FragmentNoteBinding
    // Activity kapsamında ViewModel'e erişim sağlar.
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Fragment layout'unun inflate işlemi ve binding nesnesinin başlatılması.
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'den not listesini gözlemleyip, değişiklik olduğunda RecyclerView adapter'ını günceller.
        viewModel.noteList.observe(viewLifecycleOwner) { notes ->
            // NoteAdapter ile not listesinin RecyclerView'a bağlanması.
            binding.notelist.adapter = NoteAdapter(notes, viewModel)
        }

        // "Add Note" butonuna tıklanıldığında yeni not ekleme ekranına yönlendirme.
        binding.addNoteButton.setOnClickListener {
            findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToNoteAddFragment2())
        }
    }
}
