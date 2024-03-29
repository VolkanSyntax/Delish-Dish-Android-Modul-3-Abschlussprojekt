package de.syntax.androidabschluss.local

import androidx.room.Entity
import androidx.room.PrimaryKey


// @Entity annotasyonu bu sınıfın bir veritabanı tablosuna karşılık geldiğini belirtir.
// tableName parametresi belirtilmediği için, sınıf ismi (Note) tablo adı olarak kullanılacaktır.
@Entity
data class Note(
    // @PrimaryKey, bu alanın tablonun birincil anahtarı olduğunu belirtir.
    // autoGenerate = true, bu anahtarın otomatik olarak üretileceğini belirtir.
    // Her not için benzersiz bir ID değeri otomatik olarak oluşturulur.
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Notun benzersiz kimlik numarası. Yeni bir not eklenirken bu değer otomatik olarak atanır.

    var title: String, // Notun başlığı.
    var text: String // Notun içeriği.
)
