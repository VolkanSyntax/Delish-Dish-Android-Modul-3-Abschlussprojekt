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
    // Ermöglicht den Zugriff auf UI-Komponenten durch ViewBinding.
    private lateinit var binding: FragmentNoteBinding

    // Activity kapsamında ViewModel'e erişim sağlar.
    // Ermöglicht den Zugriff auf das ViewModel innerhalb des Aktivitätskontextes.
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Fragment layout'unun inflate işlemi ve binding nesnesinin başlatılması.
        // Initialisiert das Binding-Objekt und bläht das Layout des Fragments auf.
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'den not listesini gözlemleyip, değişiklik olduğunda RecyclerView adapter'ını günceller.
        // Beobachtet die Notizenliste aus dem ViewModel und aktualisiert den RecyclerView-Adapter bei Änderungen.
        viewModel.noteList.observe(viewLifecycleOwner) { notes ->
            // NoteAdapter ile not listesinin RecyclerView'a bağlanması.
            // Verbindet die Notizenliste mit dem RecyclerView über den NoteAdapter.
            binding.notelist.adapter = NoteAdapter(notes, viewModel)
        }

        // "Add Note" butonuna tıklanıldığında yeni not ekleme ekranına yönlendirme.
        // Leitet den Benutzer zum Bildschirm für das Hinzufügen neuer Notizen weiter, wenn auf die Schaltfläche "Add Note" geklickt wird.
        binding.addNoteButton.setOnClickListener {
            findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToNoteAddFragment2())
        }
    }
}
