package com.example.veterinaryclinic.data.repository

import com.example.veterinaryclinic.data.api.ktorApiService
import com.example.veterinaryclinic.domain.repository.RestorePasswordRepository
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import javax.inject.Inject


class RestorePasswordRepositoryImpl @Inject constructor() : RestorePasswordRepository {

    override suspend fun sendMessage(email: String) {
        ktorApiService.sendRecoveryCode(mapOf("email" to email))
    }

    override suspend fun verifyCode(email: String, code: String): Boolean {
        return try {
            ktorApiService.verifyRecoveryCode(mapOf("email" to email, "code" to code))
            true
        } catch (e: HttpException) {
            false
        }
    }

    override suspend fun changePassword(email: String, code: String, newPassword: String): Boolean {
        return try {
            ktorApiService.changePassword(
                mapOf(
                    "email" to email,
                    "code" to code,
                    "newPassword" to newPassword
                )
            )
            true
        } catch (e: HttpException) {
            false
        }
    }
}
