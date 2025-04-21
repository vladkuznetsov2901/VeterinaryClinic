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
                    instruction = (item.dosage + " " + item.notes.lowercase()),
                    schedule = item.schedule,
                    dosage = item.dosage,
                    frequency = item.frequency,
                    durationDays = item.durationDays,
                    medicationDosage = medication.medicationDosage,
                    imageUrl = medication.imageUrl
                )
            } else {
                null
            }
        }
    }
}



