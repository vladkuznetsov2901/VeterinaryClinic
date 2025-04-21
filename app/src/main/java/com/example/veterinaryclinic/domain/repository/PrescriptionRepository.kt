package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.treatment.PrescriptionDto

interface PrescriptionRepository {
    suspend fun getPrescriptionsForPet(petId: Int): List<PrescriptionDto>

    suspend fun markScheduleAsTaken(scheduleId: Int): Boolean
}