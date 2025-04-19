package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.data.models.treatment.PetDto
import com.example.veterinaryclinic.domain.repository.PetRepository
import javax.inject.Inject

class GetUserPetsUseCase @Inject constructor(private val repository: PetRepository) {
    suspend operator fun invoke(userId: Int): List<PetDto> = repository.getUserPets(userId)
}