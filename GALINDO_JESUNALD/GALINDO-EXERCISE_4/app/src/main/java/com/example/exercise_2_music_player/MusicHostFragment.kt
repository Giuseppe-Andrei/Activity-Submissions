package com.example.exercise_2_music_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MusicHostFragment : Fragment() {

    private var showFavoritesOnly: Boolean = false
    private var playerFragment: PlayerFragment? = null
    private var songListFragment: SongListFragment? = null

    companion object {
        private const val ARG_FAVORITES_ONLY = "favorites_only"

        fun newInstance(favoritesOnly: Boolean): MusicHostFragment {
            val fragment = MusicHostFragment()
            val args = Bundle()
            args.putBoolean(ARG_FAVORITES_ONLY, favoritesOnly)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFavoritesOnly = arguments?.getBoolean(ARG_FAVORITES_ONLY, false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_music_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            val activity = requireActivity() as MainActivity
            val songsToShow = if (showFavoritesOnly) activity.favoriteSongs else activity.songs

            songListFragment = SongListFragment.newInstance(ArrayList(songsToShow))
            playerFragment = PlayerFragment()

            childFragmentManager.beginTransaction()
                .replace(R.id.musicListContainer, songListFragment!!)
                .replace(R.id.musicPlayerContainer, playerFragment!!)
                .commit()
        } else {
            songListFragment =
                childFragmentManager.findFragmentById(R.id.musicListContainer) as? SongListFragment
            playerFragment =
                childFragmentManager.findFragmentById(R.id.musicPlayerContainer) as? PlayerFragment
        }
    }

    fun loadSong(songData: String, position: Int) {
        playerFragment?.loadSong(songData, position)
    }

    fun playNextSong(currentPosition: Int) {
        val activity = requireActivity() as MainActivity
        val songsToShow = if (showFavoritesOnly) activity.favoriteSongs else activity.songs

        if (songsToShow.isEmpty()) return

        val nextPosition = if (currentPosition < songsToShow.size - 1) currentPosition + 1 else 0
        playerFragment?.loadSong(songsToShow[nextPosition], nextPosition)
    }

    fun playPreviousSong(currentPosition: Int) {
        val activity = requireActivity() as MainActivity
        val songsToShow = if (showFavoritesOnly) activity.favoriteSongs else activity.songs

        if (songsToShow.isEmpty()) return

        val prevPosition = if (currentPosition > 0) currentPosition - 1 else songsToShow.size - 1
        playerFragment?.loadSong(songsToShow[prevPosition], prevPosition)
    }
}