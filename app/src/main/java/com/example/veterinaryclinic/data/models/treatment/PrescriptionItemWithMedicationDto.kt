package com.example.veterinaryclinic.data.models.treatment

data class PrescriptionItemWithMedicationDto(
    val medicationName: String,
    val instruction: String?,
    val schedule: List<MedicationScheduleDto>,
    val imageUrl: String? = null
)

