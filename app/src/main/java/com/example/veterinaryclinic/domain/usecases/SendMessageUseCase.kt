package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.data.models.SendMessageRequest
import com.example.veterinaryclinic.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(request: SendMessageRequest): Boolean {
        return repository.sendMessage(request)
    }
}
