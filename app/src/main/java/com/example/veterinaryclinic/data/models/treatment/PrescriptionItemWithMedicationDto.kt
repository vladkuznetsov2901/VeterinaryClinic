package com.example.veterinaryclinic.data.models.treatment

data class PrescriptionItemWithMedicationDto(
    val medicationName: String,
    val instruction: String?,
    val schedule: List<MedicationScheduleDto>,
    val dosage: String?,
    val medicationDosage: String?,
    val frequency: String?,
    val durationDays: Int,
    val imageUrl: String? = null
)

