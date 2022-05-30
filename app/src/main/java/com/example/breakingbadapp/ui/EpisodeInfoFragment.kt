package com.example.breakingbadapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.breakingbadapp.R
import com.example.breakingbadapp.databinding.FragmentEpisodeInfoBinding
class EpisodeInfoFragment: Fragment(R.layout.fragment_episode_info) {
    private val binding by viewBinding(FragmentEpisodeInfoBinding::bind)
    private val args by navArgs<EpisodeInfoFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val persons = args.episode.characters.joinToString("") { "\n - $it" }
        binding.season.text = "Season: ${args.episode.season}"
        binding.episode.text = "Episode: ${args.episode.episode}"
        binding.date.text = "Date: ${args.episode.air_date}"
        binding.title.text = "Title: ${args.episode.title}"
        binding.persons.text = "Characters: $persons"
    }
}