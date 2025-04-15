package com.example.veterinaryclinic.data.models.chats


data class ChatDTO(
    val chatId: Int,
    val userId: Int,
    val doctorId: Int,
    val createdAt: String
)
