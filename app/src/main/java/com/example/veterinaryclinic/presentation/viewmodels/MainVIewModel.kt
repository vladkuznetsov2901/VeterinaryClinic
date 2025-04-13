package com.example.veterinaryclinic.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaryclinic.data.models.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.data.models.SpecializationDTO
import com.example.veterinaryclinic.data.models.TokenResponse
import com.example.veterinaryclinic.domain.usecases.ChatUseCases
import com.example.veterinaryclinic.domain.usecases.GetDoctorsUseCase
import com.example.veterinaryclinic.domain.usecases.GetPromoImagesUseCase
import com.example.veterinaryclinic.domain.usecases.GetSpecializationUseCase
import com.example.veterinaryclinic.domain.usecases.UserAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val userAuthUseCase: UserAuthUseCase,
    private val getPromoImagesUseCase: GetPromoImagesUseCase,
    private val getSpecializationUseCase: GetSpecializationUseCase,
    private val getDoctorsUseCase: GetDoctorsUseCase,
    private val chatUseCases: ChatUseCases
) : ViewModel() {

    private val _token = MutableStateFlow<String>("")
    val token = _token.asStateFlow()

    private val _doctors = MutableStateFlow<List<DoctorWithSpecializationDTO>>(emptyList())
    val doctors = _doctors.asStateFlow()

    fun userAuth(userName: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userAuthUseCase(userName, password)
            }.fold(
                onSuccess = { _token.value = it },
                onFailure = { Log.d("userAuth", it.message ?: "Error") }
            )
        }

    }

    suspend fun getPromoImages(): List<String> {
        return getPromoImagesUseCase()
    }

    suspend fun getSpecializations(): List<SpecializationDTO> {
        return getSpecializationUseCase()
    }

    fun getDoctors() {
        viewModelScope.launch {
            kotlin.runCatching {
                getDoctorsUseCase()
            }.fold(
                onSuccess = { _doctors.value = it },
                onFailure = { Log.d("userAuth", it.message ?: "Error") }
            )
        }
    }

    fun detectInputType(input: String): String {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        val phoneRegex = Regex("^\\+?[0-9]{10,15}$")

        return when {
            emailRegex.matches(input) -> "email"
            phoneRegex.matches(input) -> "phone"
            else -> "unknown"
        }
    }

}