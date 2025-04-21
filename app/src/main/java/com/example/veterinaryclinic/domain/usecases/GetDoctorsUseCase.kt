package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.data.models.doctors.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.domain.repository.DoctorsRepository
import javax.inject.Inject

class GetDoctorsUseCase @Inject constructor(private val repository: DoctorsRepository) {

    suspend operator fun invoke(): List<DoctorWithSpecializationDTO> {
        return repository.getDoctors()
    }


}