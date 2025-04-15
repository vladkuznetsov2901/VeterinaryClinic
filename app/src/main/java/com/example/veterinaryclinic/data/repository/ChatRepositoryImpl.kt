package com.example.veterinaryclinic.data.repository

import com.example.veterinaryclinic.data.api.ktorApiService
import com.example.veterinaryclinic.data.models.chats.ChatDTO
import com.example.veterinaryclinic.data.models.chats.CreateChatRequest
import com.example.veterinaryclinic.data.models.chats.FullChatDTO
import com.example.veterinaryclinic.data.models.chats.MessageDTO
import com.example.veterinaryclinic.data.models.chats.SendMessageRequest
import com.example.veterinaryclinic.domain.repository.ChatRepository

class ChatRepositoryImpl() : ChatRepository {

    override suspend fun getChatsByUserId(userId: Int): List<ChatDTO> {
        return ktorApiService.getChatsByUserId(userId)
    }

    override suspend fun getMessagesByChatId(chatId: Int): List<MessageDTO> {
        return ktorApiService.getMessagesByChatId(chatId)
    }

    override suspend fun sendMessage(request: SendMessageRequest): Boolean {
        val response = ktorApiService.sendMessage(request)
        return response.isSuccessful
    }

    override suspend fun createChat(request: CreateChatRequest): Int {
        val response = ktorApiService.createChat(request)
        if (response.isSuccessful) {
            return response.body()?.get("chatId") ?: throw Exception("Chat ID not found")
        } else {
            throw Exception("Chat creation failed: ${response.code()}")
        }
    }

    override suspend fun getFullChatsByUserId(role: String, userId: Int): List<FullChatDTO> {
        return ktorApiService.getFullChatsByUserId(role, userId)
    }
}
