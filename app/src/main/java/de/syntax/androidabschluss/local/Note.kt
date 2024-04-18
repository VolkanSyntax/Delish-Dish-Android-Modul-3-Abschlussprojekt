package de.syntax.androidabschluss.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity annotasyonu bu sınıfın bir veritabanı tablosuna karşılık geldiğini belirtir.
// tableName parametresi belirtilmediği için, sınıf ismi (Note) tablo adı olarak kullanılacaktır.
// Die @Entity-Anmerkung zeigt an, dass diese Klasse einer Datenbanktabelle entspricht.
// Wenn kein tableName-Parameter angegeben wird, wird der Klassenname (Note) als Tabellenname verwendet.
@Entity
data class Note(
    // @PrimaryKey, bu alanın tablonun birincil anahtarı olduğunu belirtir.
    // autoGenerate = true, bu anahtarın otomatik olarak üretileceğini belirtir.
    // Her not için benzersiz bir ID değeri otomatik olarak oluşturulur.
    // @PrimaryKey gibt an, dass dieses Feld der Primärschlüssel der Tabelle ist.
    // autoGenerate = true bedeutet, dass dieser Schlüssel automatisch generiert wird.
    // Ein einzigartiger ID-Wert wird automatisch für jede Notiz erstellt.
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Notun benzersiz kimlik numarası. Yeni bir not eklenirken bu değer otomatik olarak atanır.
    // Die einzigartige Identifikationsnummer der Notiz. Dieser Wert wird automatisch zugewiesen, wenn eine neue Notiz hinzugefügt wird.

    var title: String, // Notun başlığı.
    // Der Titel der Notiz.
    var text: String // Notun içeriği.
    // Der Inhalt der Notiz.
)
