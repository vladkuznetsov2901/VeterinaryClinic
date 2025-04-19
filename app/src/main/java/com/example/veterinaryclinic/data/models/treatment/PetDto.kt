package com.example.veterinaryclinic.data.models.treatment

data class PetDto(
    val id: Int,
    val name: String,
    val species: String,
    val isPureBreed: String,
    val dateOfBirth: String
)