package com.example.veterinaryclinic.data.repository

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.domain.repository.NotificationSender

class NotificationSenderImpl(private val context: Context) : NotificationSender {

    override fun sendNotification(notificationId: Int, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, "your_channel_id")
            .setSmallIcon(R.drawable.ic_duration)
            .setContentTitle("Напоминание")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
