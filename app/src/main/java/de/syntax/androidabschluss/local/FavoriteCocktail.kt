package de.syntax.androidabschluss.local

import androidx.room.Entity
import androidx.room.PrimaryKey
// "favorite_cocktails" adında bir tablo oluşturur. Bu tablo, kullanıcının favori olarak işaretlediği kokteylleri saklar.
@Entity(tableName = "favorite_cocktails")
data class FavoriteCocktail(
    @PrimaryKey // Kokteylin benzersiz kimlik bilgisini temsil eder ve her kayıt için anahtar görevi görür.
    val idDrink: String, // Kokteylin benzersiz ID'si. Bu ID, API'den gelen ID ile eşleştirilir.
    val strDrink: String, // Kokteylin adı.
    val strDrinkThumb: String, // Kokteylin küçük resmi (thumbnail) URL'si.
    val strCategory: String, // Kokteylin ait olduğu kategori.
    val strInstructions: String // Kokteylin hazırlanış talimatları.
)
