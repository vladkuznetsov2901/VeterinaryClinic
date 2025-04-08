package com.example.veterinaryclinic.di

import com.example.veterinaryclinic.data.repository.PromoRepositoryImpl
import com.example.veterinaryclinic.data.repository.UserRepositoryImpl
import com.example.veterinaryclinic.domain.repository.PromoRepository
import com.example.veterinaryclinic.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }

    @Provides
    fun providePromoRepository(): PromoRepository {
        return PromoRepositoryImpl()
    }

}
