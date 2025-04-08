package com.example.veterinaryclinic.domain.repository

import com.example.veterinaryclinic.data.models.Promo

interface PromoRepository {

    suspend fun getPromotions(): List<String>


}