package com.example.veterinaryclinic.data.models


data class ChatDTO(
    val chatId: Int,
    val userId: Int,
    val doctorId: Int,
    val createdAt: String
)
