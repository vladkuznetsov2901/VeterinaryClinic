package com.example.veterinaryclinic.data.repository

import com.example.veterinaryclinic.data.api.ktorApiService
import com.example.veterinaryclinic.data.models.treatment.PrescriptionDto
import com.example.veterinaryclinic.domain.repository.PrescriptionRepository

class PrescriptionRepositoryImpl : PrescriptionRepository {
    override suspend fun getPrescriptionsForPet(petId: Int): List<PrescriptionDto> =
        ktorApiService.getPrescriptionsForPet(petId)

    override suspend fun markScheduleAsTaken(scheduleId: Int): Boolean {
        return ktorApiService.markScheduleAsTaken(scheduleId).isSuccessful
    }
}