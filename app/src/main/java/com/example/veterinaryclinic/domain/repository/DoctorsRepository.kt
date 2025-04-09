package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.Promo

interface DoctorsRepository {

    suspend fun getDoctors(): List<DoctorWithSpecializationDTO>

}