package com.example.exercise_2_music_player

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    SongListFragment.OnSongSelectedListener,
    FavoritesFragment.OnFavoriteSongSelectedListener,
    ManageSongFragment.PlayerControlsListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var playerFragment: ManageSongFragment

    // MAIN SONG LIST
    private val songs = listOf(
        "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3",
        "Song 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3",
        "Song 3 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3",
        "Song 4 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3"
    )

    // FAVORITES LIST (separate)
    private val favorites = listOf(
        "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3",
        "Song 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3"
    )

    // ACTIVE PLAYLIST TRACKING
    private var currentList: List<String> = songs
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TOOLBAR
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "Music Player"
        toolbar.setLogo(R.drawable.ic_music)
        toolbar.setTitleTextColor(Color.WHITE)

        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.navigationView)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // PLAYER FRAGMENT
        playerFragment = ManageSongFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.player_container, playerFragment)
            .commit()

        // DEFAULT SCREEN
        updateUI(false)
        openContent(SongListFragment.newInstance(ArrayList(songs)))

        // BOTTOM NAV
        loadBottomNav()

        // DRAWER NAV
        navView.setNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_now_playing -> {
                    updateUI(false)
                    currentList = songs
                    openContent(SongListFragment.newInstance(ArrayList(songs)))
                }

                R.id.nav_profile -> {
                    updateUI(true)
                    openContent(ProfileFragment())
                }
            }

            drawerLayout.closeDrawers()
            true
        }
    }

    // SHOW / HIDE UI
    private fun updateUI(isProfile: Boolean) {
        val player = findViewById<View>(R.id.player_container)
        val bottomNav = findViewById<View>(R.id.bottom_nav_container)

        if (isProfile) {
            player.visibility = View.GONE
            bottomNav.visibility = View.GONE
        } else {
            player.visibility = View.VISIBLE
            bottomNav.visibility = View.VISIBLE
        }
    }

    // BOTTOM NAV
    private fun loadBottomNav() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.bottom_nav_container,
                BottomNavFragment(
                    onMusicClick = {
                        updateUI(false)
                        currentList = songs
                        openContent(SongListFragment.newInstance(ArrayList(songs)))
                    },
                    onFavoritesClick = {
                        updateUI(false)
                        currentList = favorites
                        openContent(FavoritesFragment())
                    }
                )
            )
            .commit()
    }

    // OPEN FRAGMENTS
    private fun openContent(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, fragment)
            .commit()
    }

    // SONG LIST CLICK
    override fun onSongSelected(position: Int) {
        currentList = songs
        currentPosition = position
        playSongAt()
    }

    // FAVORITES CLICK
    override fun onFavoriteSongSelected(position: Int) {
        currentList = favorites
        currentPosition = position
        playSongAt()
    }

    // CENTRAL PLAY FUNCTION
    private fun playSongAt() {
        val song = currentList[currentPosition]
        val url = song.substringAfter(" - ")
        playerFragment.playSong(song, url)
    }

    // NEXT
    override fun onNextRequested() {
        if (currentList.isEmpty()) return

        currentPosition = (currentPosition + 1) % currentList.size
        playSongAt()
    }

    // PREVIOUS
    override fun onPreviousRequested() {
        if (currentList.isEmpty()) return

        currentPosition =
            if (currentPosition - 1 < 0) currentList.size - 1
            else currentPosition - 1

        playSongAt()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) true
        else super.onOptionsItemSelected(item)
    }
}