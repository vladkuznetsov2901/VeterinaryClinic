package com.example.veterinaryclinic.data.models.chats

data class CreateChatRequest(
    val userId: Int,
    val doctorId: Int
)
