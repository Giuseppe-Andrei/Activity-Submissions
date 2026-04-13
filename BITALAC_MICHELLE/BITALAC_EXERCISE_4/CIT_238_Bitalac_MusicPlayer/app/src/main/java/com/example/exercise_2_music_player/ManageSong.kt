package com.example.exercise_2_music_player

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment

class ManageSongFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null

    private lateinit var songTitle: TextView
    private lateinit var statusText: TextView

    interface PlayerControlsListener {
        fun onNextRequested()
        fun onPreviousRequested()
    }

    private var listener: PlayerControlsListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as? PlayerControlsListener
            ?: throw RuntimeException("Activity must implement PlayerControlsListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_manage_song, container, false)

        songTitle = view.findViewById(R.id.songTitleTextView)
        statusText = view.findViewById(R.id.statusTextView)

        val playButton = view.findViewById<ImageButton>(R.id.playButton)
        val pauseButton = view.findViewById<ImageButton>(R.id.pauseButton)
        val stopButton = view.findViewById<ImageButton>(R.id.stopButton)
        val nextButton = view.findViewById<ImageButton>(R.id.nextButton)
        val prevButton = view.findViewById<ImageButton>(R.id.prevButton)

        // PLAY
        playButton.setOnClickListener {
            mediaPlayer?.let {
                if (!it.isPlaying) {
                    it.start()
                    statusText.text = "Playing"
                }
            }
        }

        // PAUSE
        pauseButton.setOnClickListener {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                    statusText.text = "Paused"
                }
            }
        }

        // STOP
        stopButton.setOnClickListener {
            mediaPlayer?.let {
                if (it.isPlaying) it.stop()
                it.reset()
                statusText.text = "Stopped"
            }
        }

        // NEXT
        nextButton.setOnClickListener {
            listener?.onNextRequested()
        }

        // PREVIOUS
        prevButton.setOnClickListener {
            listener?.onPreviousRequested()
        }

        return view
    }

    // PLAY SONG
    fun playSong(title: String, url: String) {

        // release old player safely
        mediaPlayer?.release()
        mediaPlayer = null

        songTitle.text = title.substringBefore(" - ")
        statusText.text = "Loading..."

        mediaPlayer = MediaPlayer().apply {

            setDataSource(url)

            setOnPreparedListener {
                it.start()
                statusText.text = "Playing"
            }

            setOnCompletionListener {
                statusText.text = "Completed"
            }

            setOnErrorListener { _, _, _ ->
                statusText.text = "Error playing song"
                true
            }

            prepareAsync()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}