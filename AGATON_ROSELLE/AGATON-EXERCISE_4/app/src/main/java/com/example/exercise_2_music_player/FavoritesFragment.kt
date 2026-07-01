package com.example.exercise_2_music_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Fragment for Favorites (from Bottom Navigation)
 * Shows the user's favorite songs
 */
class FavoritesFragment : Fragment() {

    private lateinit var favoritesListView: ListView

    // You can customize this list with your actual favorite songs
    private val favoriteSongs = listOf(
        "Favorite 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Favorite 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
        "Favorite 3 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
        "Favorite 4 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        favoritesListView = view.findViewById(R.id.favoritesListView)

        // Setup adapter
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, favoriteSongs)
        favoritesListView.adapter = adapter

        // Setup click listener
        favoritesListView.setOnItemClickListener { _, _, position, _ ->
            val selectedSong = favoriteSongs[position]
            Toast.makeText(
                requireContext(),
                "Playing favorite: ${selectedSong.substringBefore(" - ")}",
                Toast.LENGTH_SHORT
            ).show()
            // TODO: Integrate with music player if needed
        }

        return view
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}