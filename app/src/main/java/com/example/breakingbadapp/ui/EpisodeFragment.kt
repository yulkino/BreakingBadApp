package com.example.breakingbadapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.breakingbadapp.R
import com.example.breakingbadapp.databinding.FragmentListBinding
import com.example.breakingbadapp.recyclerview.adapters.EpisodeAdapter
import com.example.breakingbadapp.ui.viewmodels.EpisodesViewModel
import com.example.breakingbadapp.viewModelFactory

class EpisodeFragment: Fragment(R.layout.fragment_list) {
    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel by viewModels<EpisodesViewModel> { viewModelFactory() }
    private val adapter = EpisodeAdapter()
    private var canRedirect = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        canRedirect = false
        setupViews()
        subToVm()
    }

    private fun setupViews() {
        binding.listRv.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false,
        )
        binding.listRv.adapter = adapter
        binding.search.addTextChangedListener {
            if (it == null) return@addTextChangedListener
            viewModel.search(it.toString())
        }
        binding.refresh.setOnClickListener { viewModel.load() }
    }

    override fun onResume() {
        super.onResume()
        canRedirect = true
        adapter.submitList(emptyList())
        viewModel.load()
    }

    private fun subToVm() {
        viewModel.list.observe(viewLifecycleOwner) { adapter.submitList(it.toList()) }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loading.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.refresh.isVisible = it.isNotEmpty()
            binding.error.isVisible = it.isNotEmpty()
            binding.error.text = it
        }
        viewModel.infoToShow.observe(viewLifecycleOwner) {
            if (it == null || !canRedirect) return@observe
            findNavController().navigate(
                R.id.action_episodeFragment_to_episodeInfoFragment,
                EpisodeInfoFragmentArgs(it).toBundle(),
            )
        }
    }
}