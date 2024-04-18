package de.syntax.androidabschluss.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

// Klavyeyi gizleme fonksiyonu. Verilen görünüm bağlamında açık olan klavyeyi gizler.
// Funktion zum Verbergen der Tastatur. Versteckt die Tastatur im Kontext der gegebenen Ansicht.
fun Context.hideKeyBoard(it: View) {
    try {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// Uzun süre gösterilen toast mesajı fonksiyonu. Verilen mesajı uzun süre gösterir.
// Funktion für Toast-Nachrichten, die lange angezeigt werden. Zeigt die gegebene Nachricht lange an.
fun Context.longToastShow(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

// Metni panoya kopyalama fonksiyonu. Verilen metni sistem panosuna kopyalar ve başarılı olduğunda bir toast mesajı gösterir.
// Funktion zum Kopieren von Text in die Zwischenablage. Kopiert den gegebenen Text in die Systemzwischenablage und zeigt bei Erfolg eine Toast-Nachricht an.
fun Context.copyToClipboard(text: CharSequence, label: String = "Clipboard Data") {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    longToastShow("Text in die Zwischenablage kopiert!")
}
