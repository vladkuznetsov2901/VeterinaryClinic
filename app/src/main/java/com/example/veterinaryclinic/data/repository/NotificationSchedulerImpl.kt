package com.example.veterinaryclinic.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.example.veterinaryclinic.data.models.treatment.MedicationDto
import com.example.veterinaryclinic.data.models.treatment.MedicationScheduleDto
import com.example.veterinaryclinic.domain.repository.NotificationScheduler
import com.example.veterinaryclinic.presentation.views.treatment.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NotificationSchedulerImpl(private val context: Context) : NotificationScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    @RequiresApi(Build.VERSION_CODES.S)
    override fun scheduleNotification(
        schedule: MedicationScheduleDto,
        medicationName: String
    ) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("message", "Пора принять: $medicationName")
            putExtra("notification_id", schedule.scheduleId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            schedule.scheduleId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val plannedTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val plannedDate = plannedTimeFormat.parse(schedule.plannedTime)

        val calendar = Calendar.getInstance().apply {
            time = plannedDate!!
        }

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)

        }

    }

    override fun cancelNotification(scheduleId: Int) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            scheduleId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}