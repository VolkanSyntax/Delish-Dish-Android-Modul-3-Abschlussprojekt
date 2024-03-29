package de.syntax.androidabschluss.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentSettingsBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel


class SettingsFragment : Fragment() {
    // ViewModel, bu Fragment'ta kullanılan verileri yönetir.
    private val viewModel: MainViewModel by activityViewModels()
    // View Binding ile layout'a erişim sağlanır.
    private lateinit var binding: FragmentSettingsBinding
    // Kullanıcının e-posta adresini saklar.
    private lateinit var userMail: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Fragment'in layout'u inflate ediliyor ve binding nesnesi ile bağlanıyor.
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'den güncel kullanıcı bilgisi gözlemleniyor.
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                // Eğer kullanıcı bilgisi yoksa, login ekranına yönlendiriliyor.
                findNavController().navigate(R.id.loginFragment)
            } else {
                // Kullanıcı bilgisi varsa, kullanıcının e-posta adresi gösteriliyor.
                userMail = user.email.toString()
                binding.settingsText.text = "Hallo $userMail! Nett dich zu sehen!"
            }
        }

        // Çıkış yap butonuna basıldığında çıkış yapma onayı isteniyor.
        binding.logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }
    private fun showLogoutConfirmationDialog() {
        // Çıkış yapma onayı için bir AlertDialog gösteriliyor.
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Abmelden") // Dialog başlığı
            setMessage("Sind Sie sicher, dass Sie sich abmelden möchten?") // Dialog mesajı
            // Evet'e basıldığında çıkış işlemi yapılıyor.
            setPositiveButton("Ja") { dialog, which ->
                viewModel.logout()
                dialog.dismiss()
            }
            // Hayır'a basıldığında dialog kapatılıyor.
            setNegativeButton("Nein") { dialog, which ->
                dialog.dismiss()
            }
            create().show()
        }
    }
}
