package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.treatment.MedicationDto
import com.example.veterinaryclinic.data.models.treatment.MedicationScheduleDto

interface NotificationScheduler {
    fun scheduleNotification(schedule: MedicationScheduleDto, medicationName: String)
    fun cancelNotification(scheduleId: Int)
}
