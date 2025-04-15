package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.TokenResponse

interface UserRepository {

    suspend fun authenticate(username: String, password: String): TokenResponse?
    suspend fun getUserIdByToken(token: String): Int?

}