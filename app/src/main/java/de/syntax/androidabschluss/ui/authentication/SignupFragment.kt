package de.syntax.androidabschluss.ui.authentication

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentSignupBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel


class SignupFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.signupSignupButton.setOnClickListener {
            signup()
        }

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.recipeListFragment)
            }
        }

    }

    private fun signup() {
        val email = binding.signupMail.text.toString()
        val password = binding.signupPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            // AlertDialog ile kullanıcıya uyarı göster
            showIncompleteFormDialog()
        } else {
            viewModel.signup(email, password)
        }
    }

    // Kullanıcı formu eksiksiz doldurmadığında gösterilecek AlertDialog fonksiyonu
    private fun showIncompleteFormDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Unvollständige Informationen") // Dialog başlığı
            setMessage("Bitte füllen Sie Ihre E-Mail-Adresse und Ihr Passwort korrekt und vollständig aus.") // Dialog mesajı
            setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            create().show()
        }
    }
}