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
    private val viewModel: MainViewModel by activityViewModels()

    // ViewBinding ile layout'taki view'lara erişim.
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment layout'unun inflate işlemi ve binding nesnesinin başlatılması.
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "Cancel" butonuna tıklandığında önceki sayfaya geri dönme işlemi.
        binding.signupCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // "Signup" butonuna tıklandığında kayıt işlemini başlatma.
        binding.signupSignupButton.setOnClickListener {
            signup()
        }

        // Kullanıcı kayıt işlemi sonrasında otomatik giriş yapılırsa, tarif listesi ekranına yönlendirme.
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
    private fun signup() {
        val email = binding.signupMail.text.toString()
        val password = binding.signupPassword.text.toString()

        // Eğer email veya şifre boş ise uyarı gösterilir.
        if (email.isEmpty() || password.isEmpty()) {
            showIncompleteFormDialog()
        } else {
            // ViewModel üzerinden kayıt işlemi çağrılır.
            viewModel.signup(email, password)
        }
    }

    // Eksik form uyarısı için AlertDialog gösterimi.
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
