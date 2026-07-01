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
 * Fragment for My Music (from Bottom Navigation)
 * Shows the complete library of songs
 */
class MyMusicFragment : Fragment() {

    private lateinit var myMusicListView: ListView

    private val songs = listOf(
        "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Song 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        "Song 3 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_music, container, false)

        myMusicListView = view.findViewById(R.id.myMusicListView)

        // Setup adapter
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, songs)
        myMusicListView.adapter = adapter

        // Setup click listener
        myMusicListView.setOnItemClickListener { _, _, position, _ ->
            val selectedSong = songs[position]
            Toast.makeText(
                requireContext(),
                "Selected: ${selectedSong.substringBefore(" - ")}",
                Toast.LENGTH_SHORT
            ).show()
            // TODO: Integrate with music player if needed
        }

        return view
    }

    companion object {
        fun newInstance() = MyMusicFragment()
    }
}