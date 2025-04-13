package com.example.veterinaryclinic.data.models

data class SendMessageRequest(
    val chatId: Int,
    val senderType: String,
    val messageText: String
)
