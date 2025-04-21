package com.example.veterinaryclinic.data.repository

import com.example.veterinaryclinic.data.api.ktorApiService
import com.example.veterinaryclinic.data.models.doctors.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.LoginData
import com.example.veterinaryclinic.data.models.users.TokenResponse
import com.example.veterinaryclinic.domain.repository.DoctorsRepository
import javax.inject.Inject

class DoctorsRepositoryImpl @Inject constructor() : DoctorsRepository {
    override suspend fun getDoctors(): List<DoctorWithSpecializationDTO> {
        return ktorApiService.getDoctors()
    }

    override suspend fun doctorAuth(
        username: String,
        password: String
    ): TokenResponse {
        return ktorApiService.loginDoctor(LoginData(username, password))
    }

}