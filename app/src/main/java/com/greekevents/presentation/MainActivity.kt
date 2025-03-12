package com.greekevents.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.greekevents.R
import com.greekevents.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * The MainActivity is the main entry point of the application UI.
 * It hosts the NavHostFragment which handles fragment navigation
 * and sets up the bottom navigation menu.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: Starting activity initialization")
        
        try {
            super.onCreate(savedInstanceState)
            
            // Initialize view binding
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            Log.d(TAG, "onCreate: View binding initialized")
            
            // Set up the toolbar
            try {
                setSupportActionBar(binding.toolbar)
                Log.d(TAG, "onCreate: Toolbar setup complete")
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: Error setting up toolbar", e)
                // Continue without toolbar rather than crashing
            }
            
            // Set up navigation controller
            try {
                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
                
                if (navHostFragment == null) {
                    Log.e(TAG, "onCreate: NavHostFragment is null")
                    Toast.makeText(this, "Navigation initialization error", Toast.LENGTH_LONG).show()
                    return
                }
                
                navController = navHostFragment.navController
                Log.d(TAG, "onCreate: NavController initialized")
                
                // Set up bottom navigation
                val bottomNav = binding.bottomNavigation
                bottomNav.setupWithNavController(navController)
                Log.d(TAG, "onCreate: Bottom navigation setup complete")
                
                // Define top-level destinations (no back button shown)
                appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.homeFragment,
                        R.id.searchFragment,
                        R.id.categoriesFragment,
                        R.id.profileFragment
                    )
                )
                
                // Connect toolbar with navigation controller
                setupActionBarWithNavController(navController, appBarConfiguration)
                Log.d(TAG, "onCreate: Action bar setup complete")
                
                // Show/hide bottom navigation based on destination
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    // Show bottom navigation only for main destinations
                    val shouldShowBottomNav = appBarConfiguration.topLevelDestinations.contains(destination.id)
                    bottomNav.isVisible = shouldShowBottomNav
                    
                    // Change toolbar visibility for login screens
                    when (destination.id) {
                        R.id.loginFragment, R.id.registerFragment, R.id.forgotPasswordFragment -> {
                            supportActionBar?.hide()
                        }
                        else -> {
                            supportActionBar?.show()
                        }
                    }
                }
                Log.d(TAG, "onCreate: Destination listener setup complete")
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: Error setting up navigation", e)
                Toast.makeText(this, "Navigation setup error: ${e.message}", Toast.LENGTH_LONG).show()
            }
            
            Log.d(TAG, "onCreate: Activity initialization completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "onCreate: Fatal error during activity initialization", e)
            Toast.makeText(this, "Application error: ${e.message}", Toast.LENGTH_LONG).show()
            // Consider finishing the activity or showing a recovery UI
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Handle up/back navigation
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

/* Explanation for junior developers:
 * 1. @AndroidEntryPoint annotation allows Hilt to inject dependencies into the activity
 * 2. View binding is used to access views in a type-safe way without findViewById()
 * 3. NavController manages app navigation within the NavHostFragment
 * 4. AppBarConfiguration is used to configure the ActionBar/Toolbar
 * 5. setupWithNavController() connects the BottomNavigationView with navigation
 * 6. The destinations defined in appBarConfiguration are considered top-level destinations
 *    and won't show the back button in the toolbar
 * 7. addOnDestinationChangedListener() allows us to respond to navigation changes
 * 8. onSupportNavigateUp() handles up/back navigation properly with NavController
 * 9. We've added comprehensive logging and exception handling to diagnose issues
 */ 