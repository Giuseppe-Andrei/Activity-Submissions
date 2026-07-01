package com.example.exercise_4_music_player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        // Nav Controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Top-level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_now_playing,
                R.id.nav_my_music,
                R.id.nav_favorites,
                R.id.nav_profile
            ),
            findViewById(R.id.drawer_layout)
        )

        // Connect ActionBar
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Drawer navigation
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        // Bottom navigation
        findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            .setupWithNavController(navController)
    }

    // Back button behavior
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}