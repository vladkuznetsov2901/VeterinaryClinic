package com.example.veterinaryclinic.domain.usecases.notifications

import com.example.veterinaryclinic.domain.repository.NotificationSender
import javax.inject.Inject

class SendNotificationUseCase @Inject constructor(
    private val notificationSender: NotificationSender
) {
    operator fun invoke(notificationId: Int, message: String) {
        notificationSender.sendNotification(notificationId, message)
    }
}
