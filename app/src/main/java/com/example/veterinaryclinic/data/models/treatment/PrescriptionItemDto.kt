package com.example.veterinaryclinic.data.models.treatment

data class PrescriptionItemDto(
    val itemId: Int,
    val dosage: String?,
    val frequency: String?,
    val durationDays: Int,
    val startDate: String,
    val endDate: String,
    val medication: MedicationDto?,
    val schedule: List<MedicationScheduleDto>
)
