package de.syntax.androidabschluss.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// "favorite_cocktails" adında bir tablo oluşturur. Bu tablo, kullanıcının favori olarak işaretlediği kokteylleri saklar.
// Erstellt eine Tabelle namens "favorite_cocktails". Diese Tabelle speichert die Cocktails, die als Favoriten des Benutzers markiert sind.
@Entity(tableName = "favorite_cocktails")
data class FavoriteCocktail(
    @PrimaryKey // Kokteylin benzersiz kimlik bilgisini temsil eder ve her kayıt için anahtar görevi görür.
    // Dient als eindeutige Kennung für den Cocktail und als Primärschlüssel für jeden Datensatz.
    val idDrink: String, // Kokteylin benzersiz ID'si. Bu ID, API'den gelen ID ile eşleştirilir.
    // Die einzigartige ID des Cocktails. Diese ID wird mit der ID aus der API abgeglichen.
    val strDrink: String, // Kokteylin adı.
    // Der Name des Cocktails.
    val strDrinkThumb: String, // Kokteylin küçük resmi (thumbnail) URL'si.
    // Die URL des kleinen Bildes (Thumbnail) des Cocktails.
    val strCategory: String, // Kokteylin ait olduğu kategori.
    // Die Kategorie, zu der der Cocktail gehört.
    val strInstructions: String // Kokteylin hazırlanış talimatları.
    // Die Zubereitungsanweisungen für den Cocktail.
)
