package de.syntax.androidabschluss.data.models

// API'den alınan kokteyl listelerini temsil eden veri modeli.
// Datenmodell, das die Liste der Cocktails darstellt, die von der API empfangen werden.
data class CocktailList(
    val drinks: List<Cocktail>? // Kokteylleri içeren liste. Boş olabilir.
    // Liste von Cocktails. Kann null sein.
)
