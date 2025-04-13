package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.data.models.ChatDTO
import com.example.veterinaryclinic.data.models.CreateChatRequest
import com.example.veterinaryclinic.data.models.MessageDTO
import com.example.veterinaryclinic.data.models.SendMessageRequest
import javax.inject.Inject

class ChatUseCases @Inject constructor(
    private val createChatUseCase: CreateChatUseCase,
    private val getChatsByUserIdUseCase: GetChatsByUserIdUseCase,
    private val getMessagesByChatIdUseCase: GetMessagesByChatIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) {

    suspend fun createChat(request: CreateChatRequest): Int? {
        return createChatUseCase(request)
    }

    suspend fun getChatsByUserId(userId: Int): List<ChatDTO> {
        return getChatsByUserIdUseCase(userId)
    }

    suspend fun getMessagesByChatI(chatId: Int): List<MessageDTO> {
        return getMessagesByChatIdUseCase(chatId)
    }

    suspend fun sendMessage(request: SendMessageRequest): Boolean {
        return sendMessageUseCase(request)
    }


}