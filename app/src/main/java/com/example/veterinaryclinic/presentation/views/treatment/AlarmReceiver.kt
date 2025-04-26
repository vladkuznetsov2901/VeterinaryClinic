package com.example.veterinaryclinic.presentation.views.treatment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.veterinaryclinic.domain.usecases.notifications.SendNotificationUseCase
import javax.inject.Inject

class AlarmReceiver @Inject constructor(private val sendNotificationUseCase: SendNotificationUseCase)  : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("message") ?: return
        val notificationId = intent.getIntExtra("notification_id", 0)

        sendNotificationUseCase(notificationId, message)
    }
}