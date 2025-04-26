package com.example.veterinaryclinic.domain.usecases.chats

import com.example.veterinaryclinic.data.models.chats.FullChatDTO
import com.example.veterinaryclinic.domain.repository.ChatRepository
import javax.inject.Inject

class GetFullChatsByUserIdUseCase @Inject constructor(private val repository: ChatRepository) {

    suspend operator fun invoke(role: String, userId: Int): List<FullChatDTO> {
        return repository.getFullChatsByUserId(role, userId)
    }

}