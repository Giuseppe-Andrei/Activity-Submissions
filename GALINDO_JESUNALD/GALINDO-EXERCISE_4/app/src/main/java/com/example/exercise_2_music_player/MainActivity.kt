package com.example.exercise_2_music_player

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), SongControlListener {

    val songs = listOf(
        "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Song 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        "Song 3 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
    )

    val favoriteSongs = listOf(
        "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Song 3 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
    )

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainContent)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        bottomNav = findViewById(R.id.bottomNav)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Music Player"

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            replaceFragment(NowPlayingFragment(), "NOW_PLAYING")
            navigationView.setCheckedItem(R.id.nav_now_playing)
            bottomNav.selectedItemId = R.id.nav_music
        }

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_now_playing -> {
                    supportActionBar?.title = "Now Playing"
                    replaceFragment(NowPlayingFragment(), "NOW_PLAYING")
                    bottomNav.selectedItemId = R.id.nav_music
                }

                R.id.nav_profile -> {
                    supportActionBar?.title = "Profile"
                    replaceFragment(ProfileFragment(), "PROFILE")
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        bottomNav.setOnItemSelectedListener { item ->
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

            if (currentFragment is ProfileFragment) {
                navigationView.setCheckedItem(R.id.nav_now_playing)
            }

            when (item.itemId) {
                R.id.nav_music -> {
                    supportActionBar?.title = "My Music"
                    replaceFragment(MusicHostFragment.newInstance(false), "MY_MUSIC")
                    true
                }

                R.id.nav_favorites -> {
                    supportActionBar?.title = "Favorites"
                    replaceFragment(MusicHostFragment.newInstance(true), "FAVORITES")
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }

    override fun onSongSelected(songData: String, position: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        when (currentFragment) {
            is NowPlayingFragment -> currentFragment.loadSong(songData, position)
            is MusicHostFragment -> currentFragment.loadSong(songData, position)
        }
    }

    override fun onNextSong(currentPosition: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        when (currentFragment) {
            is NowPlayingFragment -> currentFragment.playNextSong(currentPosition)
            is MusicHostFragment -> currentFragment.playNextSong(currentPosition)
        }
    }

    override fun onPreviousSong(currentPosition: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        when (currentFragment) {
            is NowPlayingFragment -> currentFragment.playPreviousSong(currentPosition)
            is MusicHostFragment -> currentFragment.playPreviousSong(currentPosition)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}