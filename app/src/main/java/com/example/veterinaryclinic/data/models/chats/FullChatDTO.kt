package com.example.veterinaryclinic.data.models.chats


data class FullChatDTO(
    val chatId: Int,
    val doctorId: Int,
    val doctorName: String,
    val doctorImageUrl: String?,
    val lastMessage: String?,
    val lastMessageTime: String?
)

