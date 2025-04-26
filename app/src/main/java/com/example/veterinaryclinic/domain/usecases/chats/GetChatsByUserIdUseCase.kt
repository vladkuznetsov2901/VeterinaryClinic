package com.example.veterinaryclinic.domain.usecases.chats

import com.example.veterinaryclinic.data.models.chats.ChatDTO
import com.example.veterinaryclinic.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatsByUserIdUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(userId: Int): List<ChatDTO> {
        return repository.getChatsByUserId(userId)
    }
}
