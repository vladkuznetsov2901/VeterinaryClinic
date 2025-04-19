package com.example.veterinaryclinic.features

import com.example.veterinaryclinic.data.models.treatment.PrescriptionDto
import com.example.veterinaryclinic.data.models.treatment.PrescriptionItemWithMedicationDto

fun List<PrescriptionDto>.toMedicationDisplayList(): List<PrescriptionItemWithMedicationDto> {
    return flatMap { prescription ->
        prescription.items.mapNotNull { item ->
            val medication = item.medication
            if (medication != null) {
                PrescriptionItemWithMedicationDto(
                    medicationName = medication.medicationName,
                    instruction = item.frequency ?: item.dosage ?: "Инструкция отсутствует",
                    schedule = item.schedule,
                    imageUrl = medication.imageUrl
                )
            } else {
                null
            }
        }
    }
}



