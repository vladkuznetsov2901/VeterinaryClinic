package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.domain.repository.UserRepository
import javax.inject.Inject

class GetUserIdByTokenUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(token: String): Int? {
        return repository.getUserIdByToken(token)
    }

}