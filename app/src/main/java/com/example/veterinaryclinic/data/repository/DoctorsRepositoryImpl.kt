package com.example.veterinaryclinic.data.repository

import com.example.veterinaryclinic.data.api.ktorApiService
import com.example.veterinaryclinic.data.models.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.domain.repository.DoctorsRepository
import com.example.veterinaryclinic.domain.repository.PromoRepository
import javax.inject.Inject

class DoctorsRepositoryImpl @Inject constructor() : DoctorsRepository {
    override suspend fun getDoctors(): List<DoctorWithSpecializationDTO> {
        return ktorApiService.getDoctors()
    }

}