package de.syntax.androidabschluss.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.ItemFavoriteBinding
import de.syntax.androidabschluss.local.FavoriteMeal

class FavoriteRecipeAdapter(
    private var dataset: List<FavoriteMeal>, // Burada List<Meal> olarak değiştirildi
    private var viewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteRecipeAdapter.FavoriteRecipeViewHolder>() {

    inner class FavoriteRecipeViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteRecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: FavoriteRecipeViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.productImageView.load(item.strMealThumb)
        holder.binding.productTextViewTitle.text = item.strMeal
        holder.binding.productTextViewTags.text = item.strCategory
        holder.binding.cvFavourites.setOnClickListener {
            viewModel.getMealDetail(item.idMeal)
            holder.itemView.findNavController().navigate(R.id.recipeDetailFragment)
        }
    }
}
