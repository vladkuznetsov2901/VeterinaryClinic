package com.example.veterinaryclinic.domain.usecases.restorePassword

import com.example.veterinaryclinic.domain.repository.RestorePasswordRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repository: RestorePasswordRepository
) {
    suspend operator fun invoke(email: String, code: String, newPassword: String): Boolean {
        return repository.changePassword(email, code, newPassword)
    }
}
