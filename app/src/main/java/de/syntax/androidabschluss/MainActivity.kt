package de.syntax.androidabschluss

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import de.syntax.androidabschluss.databinding.ActivityMainBinding

// keytool -alias androiddebugkey -keystore ~/.android/debug.keystore -list -v -storepass android

class MainActivity : AppCompatActivity() {
    // ViewBinding ile ActivityMainBinding sınıfının bir örneğini başlatıyoruz.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Layout inflater kullanarak view binding'i initialize ediyoruz.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NavHostFragment'i buluyoruz ve NavController ile BottomNavigationView'i eşleştiriyoruz.
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHost.navController)

        // NavController üzerinde bir dinleyici ekleyerek, navigasyon hedefi değiştiğinde bazı işlemler yapıyoruz.
        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            // Bazı fragment'lar için BottomNavigationView'i gizliyoruz, diğerleri için görünür yapıyoruz.
            when (destination.id) {
                R.id.recipeDetailFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.cocktailDetailFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.assistantFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.loginFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.signupFragment -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        // Geri tuşu basıldığında uygulamanın nasıl tepki vereceğini özelleştiriyoruz.
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Kullanıcı geri gittiğinde NavController'ı kullanarak üst navigasyon hedefine geri dönüyoruz.
                binding.fragmentContainerView.findNavController().navigateUp()
            }
        })
    }
}
