package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.data.models.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.SpecializationDTO
import com.example.veterinaryclinic.domain.repository.DoctorsRepository
import com.example.veterinaryclinic.domain.repository.SpecializationRepository
import com.example.veterinaryclinic.domain.repository.UserRepository
import javax.inject.Inject

class GetDoctorsUseCase @Inject constructor(private val repository: DoctorsRepository) {

    suspend operator fun invoke(): List<DoctorWithSpecializationDTO> {
        return repository.getDoctors()
    }


}