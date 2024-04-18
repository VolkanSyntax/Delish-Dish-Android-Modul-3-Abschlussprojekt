package de.syntax.androidabschluss

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import de.syntax.androidabschluss.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // ViewBinding ile ActivityMainBinding sınıfının bir örneğini başlatıyoruz.
    // Initialisiert eine Instanz der Klasse ActivityMainBinding mit ViewBinding.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Layout inflater kullanarak view binding'i initialize ediyoruz.
        // Initialisiert das View Binding mit dem Layout-Inflater.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NavHostFragment'i buluyoruz ve NavController ile BottomNavigationView'i eşleştiriyoruz.
        // Findet das NavHostFragment und verknüpft es mit dem NavController und der BottomNavigationView.
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHost.navController)

        // NavController üzerinde bir dinleyici ekleyerek, navigasyon hedefi değiştiğinde bazı işlemler yapıyoruz.
        // Fügt einen Listener zum NavController hinzu, um Aktionen auszuführen, wenn sich das Navigationsziel ändert.
        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            // Bazı fragment'lar için BottomNavigationView'i gizliyoruz, diğerleri için görünür yapıyoruz.
            // Versteckt die BottomNavigationView für bestimmte Fragmente und zeigt sie für andere an.
            when (destination.id) {
                R.id.recipeDetailFragment, R.id.cocktailDetailFragment, R.id.assistantFragment,
                R.id.loginFragment, R.id.signupFragment -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        // Geri tuşu basıldığında uygulamanın nasıl tepki vereceğini özelleştiriyoruz.
        // Passt das Verhalten der Anwendung an, wenn die Zurück-Taste gedrückt wird.
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Kullanıcı geri gittiğinde NavController'ı kullanarak üst navigasyon hedefine geri dönüyoruz.
                // Kehrt zum oberen Navigationsziel zurück, indem der NavController verwendet wird, wenn der Benutzer zurückgeht.
                binding.fragmentContainerView.findNavController().navigateUp()
            }
        })
    }
}


