package com.example.exercise_2_music_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class NowPlayingFragment : Fragment() {

    private var playerFragment: PlayerFragment? = null
    private var listFragment: MusicListFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            listFragment = MusicListFragment()
            playerFragment = PlayerFragment()

            childFragmentManager.beginTransaction()
                .replace(R.id.listContainer, listFragment!!)
                .replace(R.id.playerContainer, playerFragment!!)
                .commit()
        } else {
            listFragment =
                childFragmentManager.findFragmentById(R.id.listContainer) as? MusicListFragment
            playerFragment =
                childFragmentManager.findFragmentById(R.id.playerContainer) as? PlayerFragment
        }
    }

    fun loadSong(songData: String, position: Int) {
        playerFragment?.loadSong(songData, position)
    }

    fun playNextSong(currentPosition: Int) {
        val activity = requireActivity() as MainActivity
        val nextPosition = if (currentPosition < activity.songs.size - 1) currentPosition + 1 else 0
        playerFragment?.loadSong(activity.songs[nextPosition], nextPosition)
    }

    fun playPreviousSong(currentPosition: Int) {
        val activity = requireActivity() as MainActivity
        val prevPosition = if (currentPosition > 0) currentPosition - 1 else activity.songs.size - 1
        playerFragment?.loadSong(activity.songs[prevPosition], prevPosition)
    }
}