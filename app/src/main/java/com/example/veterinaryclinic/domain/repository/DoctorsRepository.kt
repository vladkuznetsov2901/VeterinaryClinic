package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.doctors.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.users.TokenResponse

interface DoctorsRepository {

    suspend fun getDoctors(): List<DoctorWithSpecializationDTO>
    suspend fun doctorAuth(username: String, password: String): TokenResponse

}