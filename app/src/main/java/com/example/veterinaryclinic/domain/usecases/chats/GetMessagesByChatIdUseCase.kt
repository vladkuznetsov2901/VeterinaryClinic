package com.example.veterinaryclinic.domain.usecases.chats

import com.example.veterinaryclinic.data.models.chats.MessageDTO
import com.example.veterinaryclinic.domain.repository.ChatRepository
import javax.inject.Inject

class GetMessagesByChatIdUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(chatId: Int): List<MessageDTO> {
        return repository.getMessagesByChatId(chatId)
    }
}
