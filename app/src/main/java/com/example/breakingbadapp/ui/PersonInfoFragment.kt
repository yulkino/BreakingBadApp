package com.example.breakingbadapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.breakingbadapp.R
import com.example.breakingbadapp.databinding.FragmentPersonInfoBinding

class PersonInfoFragment: Fragment(R.layout.fragment_person_info) {
    private val binding by viewBinding(FragmentPersonInfoBinding::bind)
    private val args by navArgs<PersonInfoFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appearance = args.person.appearance.joinToString(", ").ifEmpty { "unknown" }
        val occupation = args.person.occupation.joinToString("") { "\n - $it" }
        binding.name.text = "Name: ${args.person.name}"
        binding.status.text = "Status: ${args.person.status}"
        binding.birthday.text = "Birthday: ${args.person.birthday}"
        binding.appearance.text = "Appeared in seasons: $appearance"
        binding.occupation.text = "Occupation: $occupation"
        Glide.with(requireContext())
            .load(args.person.img)
            .apply(RequestOptions().centerCrop())
            .into(binding.photo)
    }
}