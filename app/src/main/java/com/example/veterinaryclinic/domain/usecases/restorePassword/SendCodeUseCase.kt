package com.example.veterinaryclinic.domain.usecases.restorePassword

import com.example.veterinaryclinic.domain.repository.RestorePasswordRepository
import javax.inject.Inject

class SendCodeUseCase @Inject constructor(
    private val repository: RestorePasswordRepository
) {
    suspend operator fun invoke(email: String) {
        repository.sendMessage(email)
    }
}
