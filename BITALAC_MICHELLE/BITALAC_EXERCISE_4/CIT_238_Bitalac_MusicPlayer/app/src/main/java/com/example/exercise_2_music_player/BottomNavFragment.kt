package com.example.exercise_2_music_player

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class BottomNavFragment(
    private val onMusicClick: () -> Unit,
    private val onFavoritesClick: () -> Unit
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.bottom_navigation_bar, container, false)

        view.findViewById<LinearLayout>(R.id.layout_my_music).setOnClickListener {
            onMusicClick()
        }

        view.findViewById<LinearLayout>(R.id.layout_my_favorites).setOnClickListener {
            onFavoritesClick()
        }

        return view
    }
}