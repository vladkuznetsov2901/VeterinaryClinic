package com.example.veterinaryclinic.data.models.treatment

data class MedicationScheduleDto(
    val scheduleId: Int,
    val plannedTime: String,
    val isTaken: Boolean,
    val takenTime: String?
)
