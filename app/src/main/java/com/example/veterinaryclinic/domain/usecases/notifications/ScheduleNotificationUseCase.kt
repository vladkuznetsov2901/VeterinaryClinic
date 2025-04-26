package com.example.veterinaryclinic.domain.usecases.notifications

import com.example.veterinaryclinic.data.models.treatment.MedicationDto
import com.example.veterinaryclinic.data.models.treatment.MedicationScheduleDto
import com.example.veterinaryclinic.domain.repository.NotificationScheduler
import javax.inject.Inject

class ScheduleNotificationUseCase @Inject constructor(
    private val notificationScheduler: NotificationScheduler
) {
    operator fun invoke(schedule: MedicationScheduleDto, medicationName: String) {
        notificationScheduler.scheduleNotification(schedule, medicationName)
    }
}
