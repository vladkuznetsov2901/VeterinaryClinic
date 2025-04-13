package com.example.veterinaryclinic.data.models


data class MessageDTO(
    val messageId: Int,
    val chatId: Int,
    val senderType: String,
    val messageText: String,
    val sentAt: String
)