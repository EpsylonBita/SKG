package com.greekevents.presentation

import android.os.Bundle
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        
        // Set up navigation controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        
        // Set up bottom navigation
        val bottomNav = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)
        
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
 */ 