package com.example.breakingbadapp.recyclerview.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.breakingbadapp.databinding.ItemPersonBinding
import com.example.breakingbadapp.recyclerview.listitems.PersonListItem

class PersonViewHolder(
    private val binding: ItemPersonBinding,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(person: PersonListItem) {
        with(binding) {
            name.text = person.name
            occupation.text = person.occupation
            root.setOnClickListener { person.clicked() }
            Glide.with(binding.root.context)
                .load(person.imageUrl)
                .apply(RequestOptions().centerCrop())
                .into(photo)
        }
    }
}