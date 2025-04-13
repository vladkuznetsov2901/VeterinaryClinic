package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.ChatDTO
import com.example.veterinaryclinic.data.models.CreateChatRequest
import com.example.veterinaryclinic.data.models.MessageDTO
import com.example.veterinaryclinic.data.models.SendMessageRequest

interface ChatRepository {

    suspend fun getChatsByUserId(userId: Int): List<ChatDTO>

    suspend fun getMessagesByChatId(chatId: Int): List<MessageDTO>

    suspend fun sendMessage(request: SendMessageRequest): Boolean

    suspend fun createChat(request: CreateChatRequest): Int?
}
