package com.example.veterinaryclinic.data.models.chats


data class MessageDTO(
    val messageId: Int,
    val chatId: Int,
    val senderType: String,
    val messageText: String,
    val sentAt: String,
    val isRead: Boolean
)