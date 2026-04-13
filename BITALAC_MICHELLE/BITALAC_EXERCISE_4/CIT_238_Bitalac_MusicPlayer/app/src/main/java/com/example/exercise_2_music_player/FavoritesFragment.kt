package com.example.exercise_2_music_player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    interface OnFavoriteSongSelectedListener {
        fun onFavoriteSongSelected(position: Int)
    }

    private var listener: OnFavoriteSongSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnFavoriteSongSelectedListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.favoritesListView)
        val countText = view.findViewById<TextView>(R.id.tv_favorites_count)

        val favorites = arrayListOf(
            "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3",
            "Song 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3"
        )

        countText.text = "${favorites.size} songs"

        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.song_item_fragment,
            R.id.tvSongTitle,
            favorites
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val itemView = convertView ?: LayoutInflater.from(context)
                    .inflate(R.layout.song_item_fragment, parent, false)

                val title = itemView.findViewById<TextView>(R.id.tvSongTitle)
                val disk = itemView.findViewById<ImageView>(R.id.imgDisk)

                title.text = favorites[position]
                disk.alpha = 1f

                itemView.setOnClickListener {
                    listener?.onFavoriteSongSelected(position)
                }

                return itemView
            }
        }

        listView.adapter = adapter
    }
}