package de.syntax.androidabschluss.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.MainViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.models.Meal
import de.syntax.androidabschluss.databinding.ItemRecipeBinding

class RecipeAdapter(
    private val dataset: List<Meal>, // Changed type from String to List<Meal>
    private val viewModel: MainViewModel
): RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.productImageView.load(item.strMealThumb)
        holder.binding.productTextViewTitle.text = item.strMeal
        holder.binding.productTextViewTags.text = item.strCategory

        holder.binding.productrecipeCardView.setOnClickListener {
            viewModel.getMealDetail(item.idMeal)
            holder.itemView.findNavController().navigate(R.id.recipeDetailFragment)
        }


    }
}
