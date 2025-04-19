package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.treatment.PetDto

interface PetRepository {
    suspend fun getUserPets(userId: Int): List<PetDto>
}