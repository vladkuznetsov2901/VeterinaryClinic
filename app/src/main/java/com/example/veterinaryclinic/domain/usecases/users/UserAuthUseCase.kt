package com.example.veterinaryclinic.domain.usecases.users

import com.example.veterinaryclinic.data.models.users.TokenResponse
import com.example.veterinaryclinic.domain.repository.UserRepository
import javax.inject.Inject

class UserAuthUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(userName: String, password: String): TokenResponse? {
        return repository.authenticate(userName, password)
    }

}