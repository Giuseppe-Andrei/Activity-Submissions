package com.example.exercise_2_music_player

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment

class MusicListFragment : Fragment() {

    interface OnSongSelectedListener {

        fun onSongSelected(songData: String, position: Int)
    }

    private var listener: OnSongSelectedListener? = null

    override fun onAttach(context: Context) {

        super.onAttach(context)

        listener = context as? OnSongSelectedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        val view = inflater.inflate(
            R.layout.music_list_fragment,
            container,
            false
        )

        val listView = view.findViewById<ListView>(R.id.listView)

        val songs = (activity as MainActivity).songs

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            songs
        )

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->

            listener?.onSongSelected(
                songs[position],
                position
            )
        }

        return view
    }
}