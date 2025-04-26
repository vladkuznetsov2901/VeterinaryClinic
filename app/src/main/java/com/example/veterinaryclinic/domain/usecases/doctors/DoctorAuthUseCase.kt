package com.example.veterinaryclinic.domain.usecases.doctors

import com.example.veterinaryclinic.data.models.users.TokenResponse
import com.example.veterinaryclinic.domain.repository.DoctorsRepository
import javax.inject.Inject

class DoctorAuthUseCase @Inject constructor(private val repository: DoctorsRepository) {

    suspend operator fun invoke(userName: String, password: String): TokenResponse? {
        return repository.doctorAuth(userName, password)
    }

}