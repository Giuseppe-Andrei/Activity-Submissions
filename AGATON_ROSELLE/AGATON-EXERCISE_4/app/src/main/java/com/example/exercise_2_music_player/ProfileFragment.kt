package com.example.exercise_2_music_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Fragment for User Profile (from Navigation Drawer)
 * Displays profile picture, name, course, section, and hobbies
 */
class ProfileFragment : Fragment() {

    private lateinit var fullNameTextView: TextView
    private lateinit var courseTextView: TextView
    private lateinit var sectionTextView: TextView
    private lateinit var hobbiesTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        fullNameTextView = view.findViewById(R.id.fullNameTextView)
        courseTextView = view.findViewById(R.id.courseTextView)
        sectionTextView = view.findViewById(R.id.sectionTextView)
        hobbiesTextView = view.findViewById(R.id.hobbiesTextView)

        // Set profile data (customize this with your actual information)
        fullNameTextView.text = "Roselle Agaton"
        courseTextView.text = "Bachelor of Science in Information Technology"
        sectionTextView.text = "BSIT 3-A"
        hobbiesTextView.text = """
            • Listening to music
            • Reading books
            • Watching movies
            • Going to cafe and beach
        """.trimIndent()

        return view
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}