package de.syntax.androidabschluss.data.models

// API'den alınan yemek listelerini temsil eden veri modeli.
// Datenmodell, das die Liste der Mahlzeiten darstellt, die von der API empfangen werden.
data class MealList(
    val meals: List<Meal>? // Yemekleri içeren liste. Boş olabilir.
    // Liste von Mahlzeiten. Kann null sein.
)
