package de.syntax.androidabschluss.ui

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentSettingsBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel


class SettingsFragment : Fragment() {
    // ViewModel, bu Fragment'ta kullanılan verileri yönetir.
    // ViewModel verwaltet die Daten, die in diesem Fragment verwendet werden.
    private val viewModel: MainViewModel by activityViewModels()
    // View Binding ile layout'a erişim sağlanır.
    // View Binding ermöglicht den Zugriff auf das Layout.
    private lateinit var binding: FragmentSettingsBinding
    // Kullanıcının e-posta adresini saklar.
    // Speichert die E-Mail-Adresse des Benutzers.
    private lateinit var userMail: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Fragment'in layout'u inflate ediliyor ve binding nesnesi ile bağlanıyor.
        // Das Layout des Fragments wird aufgeblasen und mit dem Binding-Objekt verbunden.
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'den güncel kullanıcı bilgisi gözlemleniyor.
        // Die aktuellen Benutzerinformationen aus dem ViewModel werden beobachtet.
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                // Eğer kullanıcı bilgisi yoksa, login ekranına yönlendiriliyor.
                // Wenn keine Benutzerinformationen vorhanden sind, wird zum Anmeldebildschirm weitergeleitet.
                findNavController().navigate(R.id.loginFragment)
            } else {
                // Kullanıcı bilgisi varsa, kullanıcının e-posta adresi gösteriliyor.
                // Wenn Benutzerinformationen vorhanden sind, wird die E-Mail-Adresse des Benutzers angezeigt.
                userMail = user.email.toString()
                binding.settingsText.text = "Hallo $userMail! Nett dich zu sehen!"
            }
        }

        // Çıkış yap butonuna basıldığında çıkış yapma onayı isteniyor.
        // Wenn die Abmelde-Schaltfläche gedrückt wird, wird eine Bestätigung angefordert.
        binding.logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        loadThemePreference() // Tema tercihini yükler
        // Lädt die Themeneinstellung.

        binding.btnSubmit.setOnClickListener {
            val selectedId = binding.radioGroup.checkedRadioButtonId // Seçili tema ID'si alınıyor
            // Die ID des ausgewählten Themas wird abgerufen.
            when (selectedId) {
                binding.btnRadioLight.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // Açık tema seçiliyse
                    // Wenn das helle Thema ausgewählt ist
                }
                binding.btnRadioDark.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) // Koyu tema seçiliyse
                    // Wenn das dunkle Thema ausgewählt ist
                }
                binding.btnRadioDevice.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) // Sistem temasını takip et
                    // Folgt dem Systemthema
                }
            }
            saveThemePreference(selectedId) // Tema tercihini kaydeder
            // Speichert die Themeneinstellung.
        }
    }

    private fun saveThemePreference(selectedId: Int) {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("theme_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selected_theme", selectedId) // Seçili temayı kaydeder
        // Speichert das ausgewählte Thema.
        editor.apply() // Değişiklikleri uygular
        // Wendet die Änderungen an.
    }

    private fun loadThemePreference() {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("theme_preferences", MODE_PRIVATE)
        val selectedId = sharedPreferences.getInt("selected_theme", R.id.btnRadioLight) // Kayıtlı tema tercihini alır
        // Ruft die gespeicherte Themeneinstellung ab.
        binding.radioGroup.check(selectedId) // Tema seçimini ayarlar
        // Stellt die Themenauswahl ein.
    }

    private fun showLogoutConfirmationDialog() {
        // Çıkış yapma onayı için bir AlertDialog gösteriliyor.
        // Ein AlertDialog zur Bestätigung der Abmeldung wird angezeigt.
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Abmelden") // Dialog başlığı
            // Dialogtitel
            setMessage("Sind Sie sicher, dass Sie sich abmelden möchten?") // Dialog mesajı
            // Dialognachricht
            // Evet'e basıldığında çıkış işlemi yapılıyor.
            // Wenn "Ja" geklickt wird, wird der Abmeldevorgang durchgeführt.
            setPositiveButton("Ja") { dialog, which ->
                viewModel.logout()
                dialog.dismiss()
            }
            // Hayır'a basıldığında dialog kapatılıyor.
            // Wenn "Nein" geklickt wird, wird der Dialog geschlossen.
            setNegativeButton("Nein") { dialog, which ->
                dialog.dismiss()
            }
            create().show()
        }
    }
}
