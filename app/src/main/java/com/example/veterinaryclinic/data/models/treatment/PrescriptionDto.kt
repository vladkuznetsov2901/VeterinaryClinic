package com.example.veterinaryclinic.data.models.treatment

data class PrescriptionDto(
    val prescriptionId: Int,
    val prescriptionDate: String,
    val diagnosis: String?,
    val notes: String?,
    val items: List<PrescriptionItemDto>
)
