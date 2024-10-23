package com.skoda.launcher.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skoda.launcher.R


/**
 * MediaPlayerFragment is a UI component responsible for displaying and controlling media playback.
 * It integrates with the Android MediaPlayer API to handle audio playback.
 */
class MediaPlayerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media_player, container, false)
    }
}