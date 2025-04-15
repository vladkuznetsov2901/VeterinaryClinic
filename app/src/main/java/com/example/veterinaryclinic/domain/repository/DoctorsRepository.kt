package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.LoginData
import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.data.models.TokenResponse

interface DoctorsRepository {

    suspend fun getDoctors(): List<DoctorWithSpecializationDTO>
    suspend fun doctorAuth(username: String, password: String): TokenResponse

}