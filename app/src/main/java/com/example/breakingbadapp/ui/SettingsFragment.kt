package com.example.breakingbadapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.breakingbadapp.R
import com.example.breakingbadapp.dagger
import com.example.breakingbadapp.databinding.FragmentSettingsBinding
import com.example.domain.repo.SettingsRepository
import com.example.domain.repo.SettingsRepository.Companion.API_SETTINGS_KEY
import com.example.domain.repo.SettingsRepository.Companion.DB_SETTINGS_KEY
import com.example.domain.repo.SettingsRepository.Companion.NIGHT_SETTINGS_KEY
import javax.inject.Inject

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    companion object {
        private const val LAB_4_PACKAGE = "com.example.labaandruid1"
    }
    @Inject lateinit var repo: SettingsRepository
    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext().dagger().inject(this)
        updateButtons()
    }

    private fun updateButtons() {
        val api = repo.getOption(API_SETTINGS_KEY)
        val night = repo.getOption(NIGHT_SETTINGS_KEY)
        val db = repo.getOption(DB_SETTINGS_KEY)
        binding.api.text = if (api) "Disable api requests" else "Enable api requests"
        binding.api.setOnClickListener {
            repo.setOption(API_SETTINGS_KEY, !api)
            updateButtons()
        }
        binding.cache.text = if (db) "Disable caching" else "Enable caching"
        binding.cache.setOnClickListener {
            repo.setOption(DB_SETTINGS_KEY, !db)
            updateButtons()
        }
        binding.night.text = if (night) "Switch to light theme" else "Switch to dark theme"
        binding.night.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(
                if (!night) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            repo.setOption(NIGHT_SETTINGS_KEY, !night)
            updateButtons()
        }
        binding.lab.setOnClickListener {
            val manager = requireContext().packageManager
            try {
                val intent = manager.getLaunchIntentForPackage(LAB_4_PACKAGE)
                intent!!.addCategory(Intent.CATEGORY_LAUNCHER)
                requireContext().startActivity(intent)
            } catch (_: Exception) {
                Toast.makeText(requireContext(), "Lab not found on device", Toast.LENGTH_LONG).show()
            }
        }
    }
}