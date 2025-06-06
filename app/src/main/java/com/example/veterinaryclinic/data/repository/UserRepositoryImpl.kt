package com.example.veterinaryclinic.data.repository

import com.example.veterinaryclinic.data.api.ktorApiService
import com.example.veterinaryclinic.data.models.LoginData
import com.example.veterinaryclinic.data.models.users.TokenResponse
import com.example.veterinaryclinic.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override suspend fun authenticate(
        username: String,
        password: String
    ): TokenResponse {
        return ktorApiService.loginUser(LoginData(username, password))
    }

    override suspend fun getUserIdByToken(token: String): Int? {
        val response = ktorApiService.getUserIdByToken(token)
        return response.body()?.userId
    }
}