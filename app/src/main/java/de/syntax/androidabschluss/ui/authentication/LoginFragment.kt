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
import de.syntax.androidabschluss.utils.hideKeyBoard
import de.syntax.androidabschluss.viewmodel.MainViewModel

class LoginFragment : Fragment() {
    // Activity içinde paylaşılan bir ViewModel örneği. Bu ViewModel, fragmentlar arası veri paylaşımını sağlar.
    // Eine ViewModel-Instanz, die innerhalb der Activity geteilt wird. Dieses ViewModel ermöglicht den Datenaustausch zwischen Fragmenten.
    private val viewModel: MainViewModel by activityViewModels()

    // ViewBinding kullanılarak layout ile kod arasındaki bağlantı sağlanır.
    // ViewBinding wird verwendet, um eine Verbindung zwischen Layout und Code herzustellen.
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // FragmentLoginBinding ile fragment_login.xml layout dosyası inflate edilir.
        // FragmentLoginBinding wird verwendet, um die fragment_login.xml Layoutdatei zu inflaten.
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "Sign Up" butonuna tıklandığında, kullanıcıyı kayıt olma ekranına yönlendirir.
        // Bei Klick auf den "Sign Up"-Button wird der Nutzer zum Registrierungsbildschirm weitergeleitet.
        binding.loginSignupButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }

        // "Login" butonuna tıklandığında, kullanıcının girdiği e-mail ve şifre ile giriş yapma işlemi tetiklenir.
        // Beim Klicken auf den "Login"-Button wird der Login-Prozess mit der eingegebenen E-Mail und dem Passwort des Benutzers ausgelöst.
        binding.loginButton.setOnClickListener {
            val email = binding.loginEmailEdit.text.toString()
            val password = binding.loginPasswordEdit.text.toString()

            // E-mail ve şifre alanları boş değilse, ViewModel üzerinden login fonksiyonu çağırılır.
            // Wenn die E-Mail- und Passwortfelder nicht leer sind, wird die Login-Funktion über das ViewModel aufgerufen.
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                viewModel.login(email, password)
            }
        }

        // ViewModel içindeki currentUser LiveData'sı gözlemlenir.
        // Die currentUser LiveData im ViewModel wird beobachtet.
        // Eğer bir kullanıcı varsa (yani giriş başarılıysa), kullanıcıyı tarif listesi ekranına yönlendirir.
        // Wenn ein Benutzer vorhanden ist (d.h. der Login erfolgreich war), wird der Benutzer zum Rezeptlistenbildschirm weitergeleitet.
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.recipeListFragment)
            }
        }

        // Giriş yaparken klavyeyi gizlemek için ekranın boş bir alanına tıklama dinleyicisi eklenir.
        // Ein Klick-Listener wird hinzugefügt, um die Tastatur beim Einloggen zu verbergen, indem auf einen leeren Bereich des Bildschirms geklickt wird.
        binding.loginHintergrund.setOnClickListener {
            view.context.hideKeyBoard(it)
        }
    }
}
