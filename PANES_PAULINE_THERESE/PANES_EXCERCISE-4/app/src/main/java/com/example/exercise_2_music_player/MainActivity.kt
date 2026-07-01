package com.example.exercise_2_music_player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    MusicListFragment.OnSongSelectedListener,
    MusicPlayerFragment.OnNavigationListener {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    // SONG LIST
    val songs = listOf(
        "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Song 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        "Song 3 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(

            setOf(
                R.id.musicHostFragment,
                R.id.profileFragment,
                R.id.favoritesFragment
            ),

            drawerLayout
        )

        NavigationUI.setupActionBarWithNavController(
            this,
            navController,
            appBarConfiguration
        )

        navView.setupWithNavController(navController)

        bottomNav.setupWithNavController(navController)
    }

    // USER SELECTS SONG
    override fun onSongSelected(songData: String, position: Int) {

        currentIndex = position

        getPlayerFragment()?.loadSong(songData)
    }

    // PREVIOUS BUTTON
    override fun onPreviousSong() {

        currentIndex = (currentIndex - 1 + songs.size) % songs.size

        getPlayerFragment()?.loadSong(songs[currentIndex])
    }

    // NEXT BUTTON
    override fun onNextSong() {

        currentIndex = (currentIndex + 1) % songs.size

        getPlayerFragment()?.loadSong(songs[currentIndex])
    }

    private fun getPlayerFragment(): MusicPlayerFragment? {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        val musicHost =
            navHostFragment?.childFragmentManager
                ?.fragments
                ?.firstOrNull() as? MusicHostFragment

        return musicHost
            ?.childFragmentManager
            ?.findFragmentById(R.id.musicPlayerContainer) as? MusicPlayerFragment
    }

    override fun onSupportNavigateUp(): Boolean {

        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }
}