package com.example.veterinaryclinic.domain.usecases.prescription

import com.example.veterinaryclinic.domain.repository.PrescriptionRepository
import javax.inject.Inject

class MarkScheduleTakenUseCase @Inject constructor(private val prescriptionRepository: PrescriptionRepository) {

    suspend operator fun invoke(scheduleId: Int): Boolean {
        return prescriptionRepository.markScheduleAsTaken(scheduleId)
    }

}