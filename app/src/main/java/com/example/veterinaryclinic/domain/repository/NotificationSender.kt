package com.example.veterinaryclinic.domain.repository

interface NotificationSender {
    fun sendNotification(notificationId: Int, message: String)
}
