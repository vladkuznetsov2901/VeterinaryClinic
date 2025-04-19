package com.example.veterinaryclinic.data.repository

import com.example.veterinaryclinic.data.api.ktorApiService
import com.example.veterinaryclinic.data.models.treatment.PetDto
import com.example.veterinaryclinic.domain.repository.PetRepository

class PetRepositoryImpl() : PetRepository {
    override suspend fun getUserPets(userId: Int): List<PetDto> =
        ktorApiService.getPetsByUserId(userId)
}