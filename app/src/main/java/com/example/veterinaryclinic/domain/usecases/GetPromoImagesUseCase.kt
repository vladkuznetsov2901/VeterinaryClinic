package com.example.veterinaryclinic.domain.usecases

import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.domain.repository.PromoRepository
import javax.inject.Inject

class GetPromoImagesUseCase @Inject constructor(private val repository: PromoRepository) {

    suspend operator fun invoke(): List<String> {
        return repository.getPromotions()
    }

}