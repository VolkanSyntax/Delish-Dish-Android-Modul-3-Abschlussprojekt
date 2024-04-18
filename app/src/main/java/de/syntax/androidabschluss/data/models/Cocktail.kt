package de.syntax.androidabschluss.data.models

import com.squareup.moshi.Json

/**
Bu Kotlin kod parçası, bir Cocktail data modelini tanımlar. Model, bir kokteylin çeşitli özelliklerini tutmak için
kullanılır ve Moshi kütüphanesi ile JSON serileştirme/deserileştirme işlemleri için hazırlanmıştır. Her bir özellik,
JSON'dan gelen karşılık gelen veri ile eşleşmek üzere @Json annotation'ı ile işaretlenmiştir. İşte özelliklerin açıklamaları:

idDrink: Kokteylin benzersiz ID'si.
strDrink: Kokteylin adı.
strDrinkAlternate: Kokteylin alternatif adı (varsa).
strTags: Kokteylin etiketleri veya ilişkilendirilmiş anahtar kelimeler.
strVideo: Kokteylin hazırlanışını gösteren video linki (varsa).
strCategory: Kokteylin kategorisi.
strIBA: International Bartenders Association tarafından kokteyle verilen sınıflandırma (varsa).
strAlcoholic: Kokteylin alkollü mü yoksa alkolsüz mü olduğu.
strGlass: Kokteylin servis edilmesi gereken bardak tipi.
strInstructions: Kokteylin hazırlanış talimatları (varsayılan dil).
strInstructionsZHHANS: Kokteylin hazırlanış talimatları (basitleştirilmiş Çince).
strInstructionsZHHANT: Kokteylin hazırlanış talimatları (geleneksel Çince).
strDrinkThumb: Kokteylin görselinin URL'si.
strIngredient1 ... strIngredient15: Kokteylin içinde bulunan malzemeler. Kokteyller farklı sayıda malzeme içerebileceğinden, 15'e kadar malzeme desteklenir.
strMeasure1 ... strMeasure15: İlgili malzemelerin ölçüleri. Her malzeme için bir ölçü belirtilir.
strImageSource: Kokteylin görselinin kaynağı (varsa).
strCreativeCommonsConfirmed: Kokteylin görselinin Creative Commons lisansı altında olup olmadığını belirten bir işaretçi (varsa).
dateModified: Kokteylin veri kaydının son güncellendiği tarih.
Bu model, bir API'den gelen kokteyl verilerini uygulama içerisinde kolayca kullanabilmek için hazırlanmıştır.
@Json annotation'ı, Moshi kütüphanesinin JSON anahtarlarını Kotlin özellikleriyle eşleştirmesini sağlar, böylece
JSON'dan nesne oluşturma ve nesneden JSON'a dönüşüm işlemleri kolaylaşır.
 **/

data class Cocktail(
    @Json(name = "idDrink") val idDrink: String,
    @Json(name = "strDrink") val strDrink: String,
    @Json(name = "strDrinkAlternate") val strDrinkAlternate: String?,
    @Json(name = "strTags") val strTags: String?,
    @Json(name = "strVideo") val strVideo: String?,
    @Json(name = "strCategory") val strCategory: String,
    @Json(name = "strIBA") val strIBA: String?,
    @Json(name = "strAlcoholic") val strAlcoholic: String,
    @Json(name = "strGlass") val strGlass: String,
    @Json(name = "strInstructions") val strInstructions: String,
    @Json(name = "strInstructionsZHHANS") val strInstructionsZHHANS: String?,
    @Json(name = "strInstructionsZHHANT") val strInstructionsZHHANT: String?,
    @Json(name = "strDrinkThumb") val strDrinkThumb: String,
    @Json(name = "strIngredient1") val strIngredient1: String?,
    @Json(name = "strIngredient2") val strIngredient2: String?,
    @Json(name = "strIngredient3") val strIngredient3: String?,
    @Json(name = "strIngredient4") val strIngredient4: String?,
    @Json(name = "strIngredient5") val strIngredient5: String?,
    @Json(name = "strIngredient6") val strIngredient6: String?,
    @Json(name = "strIngredient7") val strIngredient7: String?,
    @Json(name = "strIngredient8") val strIngredient8: String?,
    @Json(name = "strIngredient9") val strIngredient9: String?,
    @Json(name = "strIngredient10") val strIngredient10: String?,
    @Json(name = "strIngredient11") val strIngredient11: String?,
    @Json(name = "strIngredient12") val strIngredient12: String?,
    @Json(name = "strIngredient13") val strIngredient13: String?,
    @Json(name = "strIngredient14") val strIngredient14: String?,
    @Json(name = "strIngredient15") val strIngredient15: String?,
    @Json(name = "strMeasure1") val strMeasure1: String?,
    @Json(name = "strMeasure2") val strMeasure2: String?,
    @Json(name = "strMeasure3") val strMeasure3: String?,
    @Json(name = "strMeasure4") val strMeasure4: String?,
    @Json(name = "strMeasure5") val strMeasure5: String?,
    @Json(name = "strMeasure6") val strMeasure6: String?,
    @Json(name = "strMeasure7") val strMeasure7: String?,
    @Json(name = "strMeasure8") val strMeasure8: String?,
    @Json(name = "strMeasure9") val strMeasure9: String?,
    @Json(name = "strMeasure10") val strMeasure10: String?,
    @Json(name = "strMeasure11") val strMeasure11: String?,
    @Json(name = "strMeasure12") val strMeasure12: String?,
    @Json(name = "strMeasure13") val strMeasure13: String?,
    @Json(name = "strMeasure14") val strMeasure14: String?,
    @Json(name = "strMeasure15") val strMeasure15: String?,
    @Json(name = "strImageSource") val strImageSource: String?,
    @Json(name = "strCreativeCommonsConfirmed") val strCreativeCommonsConfirmed: String?,
    @Json(name = "dateModified") val dateModified: String?
)