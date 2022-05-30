package com.example.breakingbadapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.breakingbadapp.R
import com.example.breakingbadapp.databinding.FragmentListBinding
import com.example.breakingbadapp.recyclerview.adapters.QuoteAdapter
import com.example.breakingbadapp.ui.viewmodels.QuotesViewModel
import com.example.breakingbadapp.viewModelFactory

class QuotesFragment: Fragment(R.layout.fragment_list) {
    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel by viewModels<QuotesViewModel> { viewModelFactory() }
    private val adapter = QuoteAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }
}