package de.syntax.androidabschluss.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room kütüphanesi kullanılarak tanımlanan bir veritabanı entity'si. Bu entity 'favorite_meals' isimli tabloda saklanır.
// Ein mit der Room-Bibliothek definierter Datenbank-Entity. Dieser Entity wird in der Tabelle 'favorite_meals' gespeichert.
@Entity(tableName = "favorite_meals")
data class FavoriteMeal(
    @PrimaryKey // Bu alanın her kayıt için benzersiz olduğunu belirtir, yani her yemeğin tek bir kaydı olabilir.
    // Dieses Feld kennzeichnet, dass jeder Eintrag eindeutig ist, das heißt, jede Mahlzeit kann nur einen Eintrag haben.
    val idMeal: String, // Yemeğin benzersiz ID'si. API'den gelen bu ID, veritabanında anahtar olarak kullanılır.
    // Die einzigartige ID der Mahlzeit. Diese ID, die von der API kommt, wird als Schlüssel in der Datenbank verwendet.
    val strMeal: String, // Yemeğin adı.
    // Der Name der Mahlzeit.
    val strMealThumb: String, // Yemeğin görselinin URL adresi. Bu adres, uygulamada yemeğin görselini göstermek için kullanılır.
    // Die URL des Bildes der Mahlzeit. Diese Adresse wird verwendet, um das Bild der Mahlzeit in der App anzuzeigen.
    val strCategory: String, // Yemeğin kategorisi. Örneğin: "Dessert", "Main Course" gibi.
    // Die Kategorie der Mahlzeit. Zum Beispiel: "Dessert", "Hauptgericht" usw.
    val strArea: String, // Yemeğin köken aldığı bölge veya ülke. Örneğin: "Italian", "Mexican" gibi.
    // Die Region oder das Land, aus dem die Mahlzeit stammt. Zum Beispiel: "Italienisch", "Mexikanisch" usw.
    val strInstructions: String // Yemeğin hazırlanış talimatları. Bu metin, yemeğin nasıl hazırlanacağını adım adım anlatır.
    // Die Zubereitungsanweisungen für die Mahlzeit. Dieser Text beschreibt schrittweise, wie die Mahlzeit zubereitet wird.
)
