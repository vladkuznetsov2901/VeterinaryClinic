package com.example.veterinaryclinic.domain.usecases.restorePassword

import com.example.veterinaryclinic.domain.repository.RestorePasswordRepository
import javax.inject.Inject

class VerifyCodeUseCase @Inject constructor(
    private val repository: RestorePasswordRepository
) {
    suspend operator fun invoke(email: String, code: String): Boolean {
        return repository.verifyCode(email, code)
    }
}
