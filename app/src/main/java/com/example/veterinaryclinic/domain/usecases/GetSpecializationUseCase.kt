package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.data.models.SpecializationDTO
import com.example.veterinaryclinic.domain.repository.SpecializationRepository
import com.example.veterinaryclinic.domain.repository.UserRepository
import javax.inject.Inject

class GetSpecializationUseCase @Inject constructor(private val repository: SpecializationRepository) {

    suspend operator fun invoke(): List<SpecializationDTO> {
        return repository.getSpecialization()
    }


}