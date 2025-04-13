package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.data.models.MessageDTO
import com.example.veterinaryclinic.domain.repository.ChatRepository
import javax.inject.Inject

class GetMessagesByChatIdUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(chatId: Int): List<MessageDTO> {
        return repository.getMessagesByChatId(chatId)
    }
}
