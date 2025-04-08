package com.example.veterinaryclinic.data.repository

import com.example.veterinaryclinic.data.api.ktorApiService
import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.domain.repository.PromoRepository
import javax.inject.Inject

class PromoRepositoryImpl @Inject constructor() : PromoRepository {
    override suspend fun getPromotions(): List<String> {
        return ktorApiService.getPromoImages()
    }
}