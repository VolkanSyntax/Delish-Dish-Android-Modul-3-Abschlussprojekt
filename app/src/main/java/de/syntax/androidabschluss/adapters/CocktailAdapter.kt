package de.syntax.androidabschluss.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.viewmodel.MainViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.models.Cocktail
import de.syntax.androidabschluss.databinding.ItemCocktailBinding

class CocktailAdapter(
    private val dataset: List<Cocktail>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

    inner class CocktailViewHolder(val binding: ItemCocktailBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CocktailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size

    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.productImageView.load(item.strDrinkThumb)
        holder.binding.productTextViewTitle.text = item.strDrink
        holder.binding.productTextViewTags.text = item.strCategory

        holder.binding.productcocktailCardView.setOnClickListener {

            viewModel.getCocktailDetail(item.idDrink)
            holder.itemView.findNavController().navigate(R.id.cocktailDetailFragment)
        }

    }

}
