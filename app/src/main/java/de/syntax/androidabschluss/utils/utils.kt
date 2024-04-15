package de.syntax.androidabschluss.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.hideKeyBoard(it: View) {
    try {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken,0)
    }catch (e:Exception){
        e.printStackTrace()
    }
}
fun Context.longToastShow(message: String){
    Toast.makeText(this,message, Toast.LENGTH_LONG).show()

}

fun Context.copyToClipboard(text: CharSequence, label: String = "Clipboard Data") {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    longToastShow("Text in die Zwischenablage kopiert!")
}
