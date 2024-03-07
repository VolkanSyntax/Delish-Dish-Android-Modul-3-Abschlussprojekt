package de.syntax.androidabschluss.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cocktails")
data class FavoriteCocktail(
    @PrimaryKey
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val strCategory: String,
    val strInstructions: String
)
