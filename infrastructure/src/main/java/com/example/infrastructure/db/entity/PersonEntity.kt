package com.example.infrastructure.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.Person

@Entity
data class PersonEntity(
    val name: String,
    val birthday: String,
    val occupation: String,
    val imgUrl: String,
    val status: String,
    val appearance: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    companion object {
        fun fromDomain(person: Person) =
            PersonEntity(
                person.name,
                person.birthday,
                person.occupation.joinToString(", "),
                person.img,
                person.status,
                person.appearance.joinToString(", "),
            )
    }

    fun toDomain() =
        Person(
            name,
            birthday,
            occupation.split(", "),
            imgUrl,
            status,
            appearance.split(", ").mapNotNull { x ->
                try { x.toInt() } catch(_ : Throwable) {
                    null }
            },
        )
}