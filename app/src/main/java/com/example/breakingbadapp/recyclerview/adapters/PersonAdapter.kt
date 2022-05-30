package com.example.breakingbadapp.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.breakingbadapp.databinding.ItemPersonBinding
import com.example.breakingbadapp.recyclerview.ItemCallback
import com.example.breakingbadapp.recyclerview.listitems.PersonListItem
import com.example.breakingbadapp.recyclerview.viewholders.PersonViewHolder

class PersonAdapter: ListAdapter<PersonListItem, PersonViewHolder>(ItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder =
        PersonViewHolder(ItemPersonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false),
        )

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}