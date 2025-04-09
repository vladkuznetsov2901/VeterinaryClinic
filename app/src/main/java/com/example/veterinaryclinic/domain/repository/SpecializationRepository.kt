package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.data.models.SpecializationDTO

interface SpecializationRepository {

    suspend fun getSpecialization(): List<SpecializationDTO>


}