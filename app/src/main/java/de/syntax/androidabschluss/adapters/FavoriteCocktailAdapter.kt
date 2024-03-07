package de.syntax.androidabschluss.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.ItemFavoriteBinding
import de.syntax.androidabschluss.local.FavoriteCocktail

class FavoriteCocktailAdapter(
    private val dataset: List<FavoriteCocktail>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteCocktailAdapter.FavoriteCocktailViewHolder>() {

    inner class FavoriteCocktailViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCocktailViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteCocktailViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: FavoriteCocktailViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.productImageView.load(item.strDrinkThumb)
        holder.binding.productTextViewTitle.text = item.strDrink
        holder.binding.productTextViewTags.text = item.strCategory
        holder.binding.cvFavourites.setOnClickListener {
            viewModel.getCocktailDetail(item.idDrink)
            holder.itemView.findNavController().navigate(R.id.cocktailDetailFragment)
        }
    }




}
