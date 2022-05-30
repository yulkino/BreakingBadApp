package com.example.breakingbadapp.recyclerview.listitems

import com.example.domain.models.Person

data class PersonListItem(
    val id: Int,
    val name: String,
    val occupation: String,
    val imageUrl: String,
    private val person: Person,
    val clicked: () -> Unit,
) {
    companion object {
        fun fromDomain(
            id: Int,
            person: Person,
            clicked: (Int) -> Unit,
        ) =
            PersonListItem(
                id,
                person.name,
                person.occupation.getOrElse(0) { "Occupation unknown" },
                person.img,
                person,
            ) { clicked(id) }
    }

    fun toDomain() = person
}