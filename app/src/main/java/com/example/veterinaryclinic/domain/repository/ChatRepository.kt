package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.chats.ChatDTO
import com.example.veterinaryclinic.data.models.chats.CreateChatRequest
import com.example.veterinaryclinic.data.models.chats.FullChatDTO
import com.example.veterinaryclinic.data.models.chats.MessageDTO
import com.example.veterinaryclinic.data.models.chats.SendMessageRequest

interface ChatRepository {

    suspend fun getChatsByUserId(userId: Int): List<ChatDTO>

    suspend fun getMessagesByChatId(chatId: Int): List<MessageDTO>

    suspend fun sendMessage(request: SendMessageRequest): Boolean

    suspend fun createChat(request: CreateChatRequest): Int?

    suspend fun getFullChatsByUserId(role: String, userId: Int): List<FullChatDTO>
}
