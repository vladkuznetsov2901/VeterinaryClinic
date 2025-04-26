package com.example.veterinaryclinic.domain.usecases.chats

import com.example.veterinaryclinic.data.models.chats.SendMessageRequest
import com.example.veterinaryclinic.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(request: SendMessageRequest): Boolean {
        return repository.sendMessage(request)
    }
}
