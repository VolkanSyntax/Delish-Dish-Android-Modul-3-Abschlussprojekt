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
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHost.navController)

        navHost.navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.recipeDetailFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.cocktailDetailFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.assistantFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.loginFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.signupFragment -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }


        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed(){
                binding.fragmentContainerView.findNavController().navigateUp()

            }
        })


    }
}