package com.example.veterinaryclinic.data.models.treatment


data class MedicationDto(
    val medicationId: Int,
    val medicationName: String,
    val medicationDosage: String?,
    val medicationForm: String?,
    val medicationInstruction: String?,
    val imageUrl: String?
)
