package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.data.models.TokenResponse
import com.example.veterinaryclinic.domain.repository.UserRepository
import javax.inject.Inject

class UserAuthUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(userName: String, password: String): String {
        return repository.authenticate(userName, password)?.token ?: "Error"
    }

}