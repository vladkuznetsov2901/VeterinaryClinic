package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.data.models.treatment.PrescriptionDto
import com.example.veterinaryclinic.domain.repository.PrescriptionRepository
import javax.inject.Inject

class GetPrescriptionsForPetUseCase @Inject constructor(private val repository: PrescriptionRepository) {
    suspend operator fun invoke(petId: Int): List<PrescriptionDto> =
        repository.getPrescriptionsForPet(petId)
}