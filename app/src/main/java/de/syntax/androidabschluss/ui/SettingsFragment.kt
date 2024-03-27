package de.syntax.androidabschluss.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentSettingsBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel


class SettingsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var userMail: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                findNavController().navigate(R.id.loginFragment)
            } else {
                userMail = user.email.toString()
                binding.settingsText.text = "Hallo $userMail! Nett dich zu sehen!"
            }
        }


        binding.logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }
    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Abmelden")
            setMessage("Sind Sie sicher, dass Sie sich abmelden möchten?")
            setPositiveButton("Ja") { dialog, which ->
                viewModel.logout()
                dialog.dismiss()
            }
            setNegativeButton("Nein") { dialog, which ->
                dialog.dismiss()
            }
            create().show()
        }
    }
}