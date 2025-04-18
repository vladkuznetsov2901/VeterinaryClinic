package com.example.veterinaryclinic.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaryclinic.domain.repository.RestorePasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setCode(value: String) {
        _code.value = value
    }

    fun sendCode(email: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                restorePasswordRepository.sendMessage(email)
                _uiState.value = UiState.EmailSent
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Не удалось отправить код: ${e.localizedMessage}")
            }
        }
    }

    fun verifyCode(email: String, code: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                restorePasswordRepository.verifyCode(email, code)
                _uiState.value = UiState.CodeVerified
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Код неверен или истёк")
            }
        }
    }

    fun changePassword(password: String, confirmPassword: String, email: String, code: String) {
        viewModelScope.launch {
            if (password != confirmPassword) {
                _uiState.value = UiState.Error("Пароли не совпадают")
                return@launch
            }

            _uiState.value = UiState.Loading
            try {
                restorePasswordRepository.changePassword(email, code, password)
                _uiState.value = UiState.PasswordChanged
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Не удалось изменить пароль")
            }
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}