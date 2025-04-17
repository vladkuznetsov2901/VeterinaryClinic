package com.example.veterinaryclinic.domain.repository

interface RestorePasswordRepository {

    suspend fun sendMessage(email: String)

    suspend fun verifyCode(email: String, code: String): Boolean

    suspend fun changePassword(email: String, code: String, newPassword: String): Boolean
}
