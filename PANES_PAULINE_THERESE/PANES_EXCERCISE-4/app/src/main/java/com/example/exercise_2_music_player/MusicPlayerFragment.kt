package com.example.exercise_2_music_player

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment

class MusicPlayerFragment : Fragment() {

    interface OnNavigationListener {

        fun onPreviousSong()

        fun onNextSong()
    }

    private var listener: OnNavigationListener? = null

    private var mediaPlayer: MediaPlayer? = null

    private lateinit var songTitle: TextView

    private lateinit var status: TextView

    private var currentSongUrl: String? = null

    override fun onAttach(context: Context) {

        super.onAttach(context)

        listener = context as? OnNavigationListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        val view = inflater.inflate(
            R.layout.music_player_fragment,
            container,
            false
        )

        songTitle = view.findViewById(R.id.songTitle)

        status = view.findViewById(R.id.status)

        val playButton = view.findViewById<Button>(R.id.playButton)
        val pauseButton = view.findViewById<Button>(R.id.pauseButton)
        val stopButton = view.findViewById<Button>(R.id.stopButton)
        val prevButton = view.findViewById<Button>(R.id.prevButton)
        val nextButton = view.findViewById<Button>(R.id.nextButton)

        playButton.setOnClickListener {

            playSong()
        }

        pauseButton.setOnClickListener {

            mediaPlayer?.pause()

            status.text = "Paused"
        }

        stopButton.setOnClickListener {

            stopSong()
        }

        prevButton.setOnClickListener {

            listener?.onPreviousSong()
        }

        nextButton.setOnClickListener {

            listener?.onNextSong()
        }

        return view
    }

    fun loadSong(songData: String) {

        stopSong()

        val parts = songData.split(" - ")

        val title = parts[0]

        val url = parts[1]

        songTitle.text = title

        currentSongUrl = url

        status.text = "Ready"
    }

    private fun playSong() {

        if (currentSongUrl == null) return

        mediaPlayer = MediaPlayer().apply {

            setDataSource(currentSongUrl)

            prepare()

            start()
        }

        status.text = "Playing"
    }

    private fun stopSong() {

        mediaPlayer?.release()

        mediaPlayer = null

        status.text = "Stopped"
    }
}