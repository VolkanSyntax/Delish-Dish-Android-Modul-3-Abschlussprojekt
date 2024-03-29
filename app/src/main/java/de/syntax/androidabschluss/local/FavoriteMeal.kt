package de.syntax.androidabschluss.local

import androidx.room.Entity
import androidx.room.PrimaryKey


// Room kütüphanesi kullanılarak tanımlanan bir veritabanı entity'si. Bu entity 'favorite_meals' isimli tabloda saklanır.
@Entity(tableName = "favorite_meals")
data class FavoriteMeal(
    @PrimaryKey // Bu alanın her kayıt için benzersiz olduğunu belirtir, yani her yemeğin tek bir kaydı olabilir.
    val idMeal: String, // Yemeğin benzersiz ID'si. API'den gelen bu ID, veritabanında anahtar olarak kullanılır.
    val strMeal: String, // Yemeğin adı.
    val strMealThumb: String, // Yemeğin görselinin URL adresi. Bu adres, uygulamada yemeğin görselini göstermek için kullanılır.
    val strCategory: String, // Yemeğin kategorisi. Örneğin: "Dessert", "Main Course" gibi.
    val strArea: String, // Yemeğin köken aldığı bölge veya ülke. Örneğin: "Italian", "Mexican" gibi.
    val strInstructions: String // Yemeğin hazırlanış talimatları. Bu metin, yemeğin nasıl hazırlanacağını adım adım anlatır.
)
