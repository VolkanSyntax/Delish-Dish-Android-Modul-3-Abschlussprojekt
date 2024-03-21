package de.syntax.androidabschluss.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.ItemNoteBinding
import de.syntax.androidabschluss.local.Note
import de.syntax.androidabschluss.ui.NoteFragmentDirections
import de.syntax.androidabschluss.viewmodel.MainViewModel

class NoteAdapter(
    private val dataset: List<Note>,
    private val viewModel: MainViewModel,
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val note = dataset[position]
        holder.binding.itemNameText.text = note.title
        holder.binding.itemFoodText.text = note.text

        holder.binding.deleteCardIcon.setOnClickListener {
            viewModel.deleteNote(note)
        }

        holder.binding.itemLayout.setOnClickListener {
            val navController = holder.binding.itemLayout.findNavController()
            navController.navigate(NoteFragmentDirections.actionNoteFragmentToNoteEditFragment(note.id))
        }



    }


}