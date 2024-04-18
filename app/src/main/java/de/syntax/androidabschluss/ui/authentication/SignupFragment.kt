package de.syntax.androidabschluss.ui.authentication

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentSignupBinding
import de.syntax.androidabschluss.utils.hideKeyBoard
import de.syntax.androidabschluss.viewmodel.MainViewModel

class SignupFragment : Fragment() {
    // Activity kapsamında tanımlı bir ViewModel'e erişim. Bu ViewModel, kayıt işlemlerini yönetir.
    // Zugriff auf ein ViewModel, das innerhalb der Activity definiert ist. Dieses ViewModel verwaltet die Registrierungsprozesse.
    private val viewModel: MainViewModel by activityViewModels()

    // ViewBinding ile layout'taki view'lara erişim.
    // Zugriff auf Views im Layout mittels ViewBinding.
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment layout'unun inflate işlemi ve binding nesnesinin başlatılması.
        // Inflation des Fragment-Layouts und Initialisierung des Binding-Objekts.
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "Cancel" butonuna tıklandığında önceki sayfaya geri dönme işlemi.
        // Aktion, um zur vorherigen Seite zurückzukehren, wenn auf die "Abbrechen"-Schaltfläche geklickt wird.
        binding.signupCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // "Signup" butonuna tıklandığında kayıt işlemini başlatma.
        // Startet den Registrierungsprozess, wenn auf die "Registrieren"-Schaltfläche geklickt wird.
        binding.signupSignupButton.setOnClickListener {
            signup()
        }

        // Kullanıcı kayıt işlemi sonrasında otomatik giriş yapılırsa, tarif listesi ekranına yönlendirme.
        // Leitet nach der automatischen Anmeldung nach der Registrierung zum Rezeptlistenbildschirm weiter.
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.recipeListFragment)
            }
        }
        binding.signupHintergrund.setOnClickListener {
            view.context.hideKeyBoard(it)
        }
    }

    // Kayıt işlemini gerçekleştiren fonksiyon.
    // Funktion, die den Registrierungsprozess durchführt.
    private fun signup() {
        val email = binding.signupMail.text.toString()
        val password = binding.signupPassword.text.toString()

        // Eğer email veya şifre boş ise uyarı gösterilir.
        // Zeigt eine Warnung an, wenn E-Mail oder Passwort leer sind.
        if (email.isEmpty() || password.isEmpty()) {
            showIncompleteFormDialog()
        } else {
            // ViewModel üzerinden kayıt işlemi çağrılır.
            // Ruft den Registrierungsprozess über das ViewModel auf.
            viewModel.signup(email, password)
        }
    }

    // Eksik form uyarısı için AlertDialog gösterimi.
    // Zeigt einen AlertDialog für eine Warnung bei unvollständigem Formular.
    private fun showIncompleteFormDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Unvollständige Informationen") // Başlık
            setMessage("Bitte füllen Sie Ihre E-Mail-Adresse und Ihr Passwort korrekt und vollständig aus.") // Mesaj
            setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            create().show()
        }
    }
}
