package com.example.exercise_2_music_player

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class MusicHostFragment : Fragment(R.layout.fragment_music_host) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {

            childFragmentManager.beginTransaction()

                .replace(
                    R.id.musicListContainer,
                    MusicListFragment()
                )

                .replace(
                    R.id.musicPlayerContainer,
                    MusicPlayerFragment()
                )

                .commit()
        }
    }
}