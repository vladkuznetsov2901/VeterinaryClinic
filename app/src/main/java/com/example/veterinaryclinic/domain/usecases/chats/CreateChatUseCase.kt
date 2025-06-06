package com.example.veterinaryclinic.domain.usecases.chats

import com.example.veterinaryclinic.data.models.chats.CreateChatRequest
import com.example.veterinaryclinic.domain.repository.ChatRepository
import javax.inject.Inject

class CreateChatUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(request: CreateChatRequest): Int? {
        return repository.createChat(request)
    }
}
