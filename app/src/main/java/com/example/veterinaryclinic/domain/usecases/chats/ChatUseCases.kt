package com.example.veterinaryclinic.domain.usecases.chats

import com.example.veterinaryclinic.data.models.chats.ChatDTO
import com.example.veterinaryclinic.data.models.chats.CreateChatRequest
import com.example.veterinaryclinic.data.models.chats.FullChatDTO
import com.example.veterinaryclinic.data.models.chats.MessageDTO
import com.example.veterinaryclinic.data.models.chats.SendMessageRequest
import javax.inject.Inject

class ChatUseCases @Inject constructor(
    private val createChatUseCase: CreateChatUseCase,
    private val getChatsByUserIdUseCase: GetChatsByUserIdUseCase,
    private val getMessagesByChatIdUseCase: GetMessagesByChatIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getFullChatsByUserIdUseCase: GetFullChatsByUserIdUseCase
) {

    suspend fun createChat(request: CreateChatRequest): Int? {
        return createChatUseCase(request)
    }

    suspend fun getChatsByUserId(userId: Int): List<ChatDTO> {
        return getChatsByUserIdUseCase(userId)
    }

    suspend fun getMessagesByChatId(chatId: Int): List<MessageDTO> {
        return getMessagesByChatIdUseCase(chatId)
    }

    suspend fun sendMessage(request: SendMessageRequest): Boolean {
        return sendMessageUseCase(request)
    }

    suspend fun getFullChatsByUserId(role: String, userId: Int): List<FullChatDTO> {
        return getFullChatsByUserIdUseCase(role, userId)
    }


}