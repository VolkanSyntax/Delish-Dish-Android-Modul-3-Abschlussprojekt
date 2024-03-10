package de.syntax.androidabschluss.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentLoginBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel


class LoginFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginSignupButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmailEdit.text.toString()
            val password = binding.loginPasswordEdit.text.toString()

            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                viewModel.login(email, password)
            }
        }

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.recipeListFragment)
            }
        }
    }
}