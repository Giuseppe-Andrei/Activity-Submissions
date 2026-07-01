package com.example.exercise_2_music_player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class SongListFragment : Fragment() {

    private lateinit var listener: SongControlListener
    private lateinit var songsListView: ListView
    private var songs: ArrayList<String> = arrayListOf()

    companion object {
        private const val ARG_SONGS = "songs"

        fun newInstance(songList: ArrayList<String>): SongListFragment {
            val fragment = SongListFragment()
            val args = Bundle()
            args.putStringArrayList(ARG_SONGS, songList)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SongControlListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement SongControlListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songs = arguments?.getStringArrayList(ARG_SONGS) ?: arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_music_list, container, false)
        songsListView = view.findViewById(R.id.songsListView)

        val displaySongs = songs.map { it.substringBefore(" - ") }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, displaySongs)
        songsListView.adapter = adapter

        songsListView.setOnItemClickListener { _, _, position, _ ->
            listener.onSongSelected(songs[position], position)
        }

        return view
    }
}