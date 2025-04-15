package com.example.veterinaryclinic.data.models.chats

data class SendMessageRequest(
    val chatId: Int,
    val messageText: String,
    val senderType: String,
)
