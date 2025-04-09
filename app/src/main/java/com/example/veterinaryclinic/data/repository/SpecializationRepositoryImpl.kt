package com.example.veterinaryclinic.data.repository

import com.example.veterinaryclinic.data.api.ktorApiService
import com.example.veterinaryclinic.data.models.SpecializationDTO
import com.example.veterinaryclinic.domain.repository.PromoRepository
import com.example.veterinaryclinic.domain.repository.SpecializationRepository
import javax.inject.Inject

class SpecializationRepositoryImpl @Inject constructor() : SpecializationRepository {
    override suspend fun getSpecialization(): List<SpecializationDTO> {
        return ktorApiService.getSpecializations()
    }

}