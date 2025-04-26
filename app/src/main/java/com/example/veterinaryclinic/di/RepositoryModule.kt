package com.example.veterinaryclinic.di

import android.content.Context
import com.example.veterinaryclinic.data.repository.ChatRepositoryImpl
import com.example.veterinaryclinic.data.repository.DoctorsRepositoryImpl
import com.example.veterinaryclinic.data.repository.NotificationSchedulerImpl
import com.example.veterinaryclinic.data.repository.NotificationSenderImpl
import com.example.veterinaryclinic.data.repository.PetRepositoryImpl
import com.example.veterinaryclinic.data.repository.PrescriptionRepositoryImpl
import com.example.veterinaryclinic.data.repository.PromoRepositoryImpl
import com.example.veterinaryclinic.data.repository.RestorePasswordRepositoryImpl
import com.example.veterinaryclinic.data.repository.SpecializationRepositoryImpl
import com.example.veterinaryclinic.data.repository.UserRepositoryImpl
import com.example.veterinaryclinic.domain.repository.ChatRepository
import com.example.veterinaryclinic.domain.repository.DoctorsRepository
import com.example.veterinaryclinic.domain.repository.NotificationScheduler
import com.example.veterinaryclinic.domain.repository.NotificationSender
import com.example.veterinaryclinic.domain.repository.PetRepository
import com.example.veterinaryclinic.domain.repository.PrescriptionRepository
import com.example.veterinaryclinic.domain.repository.PromoRepository
import com.example.veterinaryclinic.domain.repository.RestorePasswordRepository
import com.example.veterinaryclinic.domain.repository.SpecializationRepository
import com.example.veterinaryclinic.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Provides
    fun provideChatRepository(): ChatRepository {
        return ChatRepositoryImpl()
    }

    @Provides
    fun provideRestorePasswordRepository(): RestorePasswordRepository {
        return RestorePasswordRepositoryImpl()
    }

    @Provides
    fun providePetsRepository(): PetRepository {
        return PetRepositoryImpl()
    }

    @Provides
    fun providePrescriptionRepository(): PrescriptionRepository {
        return PrescriptionRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideNotificationScheduler(
        @ApplicationContext context: Context
    ): NotificationScheduler {
        return NotificationSchedulerImpl(context)
    }

    @Provides
    @Singleton
    fun provideNotificationSender(
        @ApplicationContext context: Context
    ): NotificationSender {
        return NotificationSenderImpl(context)
    }

}
