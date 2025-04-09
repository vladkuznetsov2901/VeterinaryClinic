package com.example.veterinaryclinic.di

import com.example.veterinaryclinic.data.repository.DoctorsRepositoryImpl
import com.example.veterinaryclinic.data.repository.PromoRepositoryImpl
import com.example.veterinaryclinic.data.repository.SpecializationRepositoryImpl
import com.example.veterinaryclinic.data.repository.UserRepositoryImpl
import com.example.veterinaryclinic.domain.repository.DoctorsRepository
import com.example.veterinaryclinic.domain.repository.PromoRepository
import com.example.veterinaryclinic.domain.repository.SpecializationRepository
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

    @Provides
    fun provideSpecializationRepository(): SpecializationRepository {
        return SpecializationRepositoryImpl()
    }

    @Provides
    fun provideDoctorsRepository(): DoctorsRepository {
        return DoctorsRepositoryImpl()
    }

}
