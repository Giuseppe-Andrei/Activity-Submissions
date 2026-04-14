package com.example.exercise_2_music_player

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

/**
 * Main Activity with Navigation Drawer and Bottom Navigation
 *
 * Navigation Drawer items:
 * - Now Playing: Shows the music list
 * - Profile: Shows user profile
 *
 * Bottom Navigation items:
 * - My Music: Complete library of songs
 * - Favorites: Favorite songs
 */
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: MaterialToolbar
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        // Initialize views
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.nav_view)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set up the toolbar as the action bar
        setSupportActionBar(toolbar)

        // Get the NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configure the AppBar with the navigation drawer
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_my_music,      // Bottom nav items
                R.id.nav_favorites,
                R.id.nav_now_playing,   // Drawer items
                R.id.nav_profile
            ),
            drawerLayout
        )

        // Setup ActionBar with NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Setup Navigation Drawer
        setupNavigationDrawer()

        // Setup Bottom Navigation
        setupBottomNavigation()
    }

    /**
     * Setup Navigation Drawer click listeners
     */
    private fun setupNavigationDrawer() {
        navigationView.setupWithNavController(navController)

        // Close drawer when item is selected
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_now_playing -> {
                    navController.navigate(R.id.nav_now_playing)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_profile -> {
                    navController.navigate(R.id.nav_profile)
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Setup Bottom Navigation click listeners
     */
    private fun setupBottomNavigation() {
        bottomNavigationView.setupWithNavController(navController)

        // Update bottom navigation selected item when destination changes
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_my_music, R.id.nav_favorites -> {
                    // Keep the bottom nav item selected
                    bottomNavigationView.menu.findItem(destination.id)?.isChecked = true
                }
            }
        }
    }

    /**
     * Handle up navigation (back button and drawer toggle)
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Handle back button press
     */
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}