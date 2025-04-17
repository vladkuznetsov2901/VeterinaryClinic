package com.example.veterinaryclinic.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaryclinic.domain.repository.RestorePasswordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestorePasswordViewModel @Inject constructor(
    private val restorePasswordRepository: RestorePasswordRepository
) : ViewModel() {

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> = _email

    private val _code = MutableStateFlow<String>("")
    val code: StateFlow<String> = _code

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object EmailSent : UiState()
        data object CodeVerified : UiState()
        data object PasswordChanged : UiState()
        data class Error(val message: String) : UiState()
    }

    fun sendEmail() {
        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                restorePasswordRepository.sendMessage(email)
                uiState = UiState.EmailSent
            } catch (e: Exception) {
                uiState = UiState.Error("Не удалось отправить код: ${e.localizedMessage}")
            }
        }
    }

    fun verifyCode() {
        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                restorePasswordRepository.verifyCode(email, code)
                uiState = UiState.CodeVerified
            } catch (e: Exception) {
                uiState = UiState.Error("Код неверен или истёк")
            }
        }
    }

    fun changePassword() {
        viewModelScope.launch {
            if (password != confirmPassword) {
                uiState = UiState.Error("Пароли не совпадают")
                return@launch
            }

            uiState = UiState.Loading
            try {
                restorePasswordRepository.changePassword(email, code, password)
                uiState = UiState.PasswordChanged
            } catch (e: Exception) {
                uiState = UiState.Error("Не удалось изменить пароль")
            }
        }
    }

    fun resetState() {
        uiState = UiState.Idle
    }
}