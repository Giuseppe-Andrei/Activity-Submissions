package com.example.exercise_2_music_player

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment

class SongListFragment : Fragment() {

    interface OnSongSelectedListener {
        fun onSongSelected(position: Int)
        fun onNextRequested()
        fun onPreviousRequested()
    }

    private lateinit var listener: OnSongSelectedListener
    private lateinit var songs: ArrayList<String>

    companion object {
        private const val ARG_SONGS = "songs"

        fun newInstance(songs: ArrayList<String>): SongListFragment {
            val fragment = SongListFragment()
            val args = Bundle()
            args.putStringArrayList(ARG_SONGS, songs)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnSongSelectedListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songs = arguments?.getStringArrayList(ARG_SONGS) ?: arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_song_list, container, false)

        val listView = view.findViewById<ListView>(R.id.songsListView)
        val countText = view.findViewById<TextView>(R.id.tv_songs_count)

        countText.text = if (songs.size == 1) "1 song" else "${songs.size} songs"

        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.song_item_fragment,
            R.id.tvSongTitle,
            songs
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val itemView = convertView ?: inflater.inflate(R.layout.song_item_fragment, parent, false)

                val title = itemView.findViewById<TextView>(R.id.tvSongTitle)
                val disk = itemView.findViewById<ImageView>(R.id.imgDisk)

                title.text = songs[position]
                disk.alpha = 1f

                return itemView
            }
        }

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            listener.onSongSelected(position)
        }

        return view
    }
}