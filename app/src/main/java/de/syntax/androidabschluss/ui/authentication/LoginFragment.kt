package de.syntax.androidabschluss.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentLoginBinding
import de.syntax.androidabschluss.viewmodel.MainViewModel


class LoginFragment : Fragment() {
    // Activity içinde paylaşılan bir ViewModel örneği. Bu ViewModel, fragmentlar arası veri paylaşımını sağlar.
    private val viewModel: MainViewModel by activityViewModels()

    // ViewBinding kullanılarak layout ile kod arasındaki bağlantı sağlanır.
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // FragmentLoginBinding ile fragment_login.xml layout dosyası inflate edilir.
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "Sign Up" butonuna tıklandığında, kullanıcıyı kayıt olma ekranına yönlendirir.
        binding.loginSignupButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }

        // "Login" butonuna tıklandığında, kullanıcının girdiği e-mail ve şifre ile giriş yapma işlemi tetiklenir.
        binding.loginButton.setOnClickListener {
            val email = binding.loginEmailEdit.text.toString()
            val password = binding.loginPasswordEdit.text.toString()

            // E-mail ve şifre alanları boş değilse, ViewModel üzerinden login fonksiyonu çağırılır.
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                viewModel.login(email, password)
            }
        }

        // ViewModel içindeki currentUser LiveData'sı gözlemlenir.
        // Eğer bir kullanıcı varsa (yani giriş başarılıysa), kullanıcıyı tarif listesi ekranına yönlendirir.
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.recipeListFragment)
            }
        }
    }
}
