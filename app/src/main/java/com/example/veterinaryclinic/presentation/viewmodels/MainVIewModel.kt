package com.example.veterinaryclinic.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaryclinic.data.models.AuthResult
import com.example.veterinaryclinic.data.models.chats.ChatDTO
import com.example.veterinaryclinic.data.models.chats.CreateChatRequest
import com.example.veterinaryclinic.data.models.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.chats.MessageDTO
import com.example.veterinaryclinic.data.models.chats.SendMessageRequest
import com.example.veterinaryclinic.data.models.SpecializationDTO
import com.example.veterinaryclinic.data.models.chats.FullChatDTO
import com.example.veterinaryclinic.domain.usecases.ChatUseCases
import com.example.veterinaryclinic.domain.usecases.DoctorAuthUseCase
import com.example.veterinaryclinic.domain.usecases.GetDoctorsUseCase
import com.example.veterinaryclinic.domain.usecases.GetPromoImagesUseCase
import com.example.veterinaryclinic.domain.usecases.GetSpecializationUseCase
import com.example.veterinaryclinic.domain.usecases.GetUserIdByTokenUseCase
import com.example.veterinaryclinic.domain.usecases.UserAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userAuthUseCase: UserAuthUseCase,
    private val doctorAuthUseCase: DoctorAuthUseCase,
    private val getUserIdByTokenUseCase: GetUserIdByTokenUseCase,
    private val getPromoImagesUseCase: GetPromoImagesUseCase,
    private val getSpecializationUseCase: GetSpecializationUseCase,
    private val getDoctorsUseCase: GetDoctorsUseCase,
    private val chatUseCases: ChatUseCases
) : ViewModel() {

    private val _token = MutableStateFlow<String>("")
    val token = _token.asStateFlow()

    private val _authResult = MutableStateFlow<AuthResult?>(null)
    val authResult: StateFlow<AuthResult?> = _authResult

    private val _role = MutableStateFlow("")
    val role = _role.asStateFlow()


    private val _userId = MutableStateFlow<Int>(0)
    val userId = _userId.asStateFlow()

    private val _promoImages = MutableStateFlow<List<String>>(emptyList())
    val promoImages: StateFlow<List<String>> = _promoImages

    private val _specializations = MutableStateFlow<List<SpecializationDTO>>(emptyList())
    val specializations: StateFlow<List<SpecializationDTO>> = _specializations

    private val _doctors = MutableStateFlow<List<DoctorWithSpecializationDTO>>(emptyList())
    val doctors = _doctors.asStateFlow()

    private val _fullChatsByUserId = MutableStateFlow<List<FullChatDTO>>(emptyList())
    val fullChatsByUserId = _fullChatsByUserId.asStateFlow()

    private val _messages = MutableStateFlow<List<MessageDTO>>(emptyList())
    val messages = _messages.asStateFlow()

    private var isLoaded = false

    suspend fun userAuth(login: String, password: String) {
        try {
            val authResponse = userAuthUseCase(login, password)
            if (authResponse != null) {
                _token.value = authResponse.token
                _authResult.value = AuthResult.Success(authResponse.token)
            }

        } catch (e: Exception) {
            _authResult.value = AuthResult.Error("Неверный логин или пароль")
        }
    }

    suspend fun doctorAuth(userName: String, password: String) {
        try {
            val authResponse = doctorAuthUseCase(userName, password)
            if (authResponse != null) {
                _token.value = authResponse.token
                _authResult.value = AuthResult.Success(authResponse.token)
            }
        } catch (e: HttpException) {
            Log.e("Auth", "HTTP error: ${e.code()} ${e.message()}")
        } catch (e: Exception) {
            Log.e("Auth", "Unexpected error: ${e.message}")
        }

    }

    fun getUserIdByToken(token: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                getUserIdByTokenUseCase(token)
            }.fold(
                onSuccess = {
                    if (it != null) {
                        _userId.value = it
                    }
                },
                onFailure = { Log.d("userAuth", it.message ?: "Error") }
            )
        }
    }


    fun loadHomeDataOnce() {
        if (isLoaded) return
        isLoaded = true

        viewModelScope.launch {
            getPromoImages()
            getSpecializations()
            getDoctors()
        }
    }

    fun getPromoImages() {
        viewModelScope.launch {
            kotlin.runCatching {
                getPromoImagesUseCase()
            }.fold(
                onSuccess = {
                    _promoImages.value = it
                },
                onFailure = { Log.d("_promoImages", it.message ?: "Error") }
            )
        }
    }

    fun getSpecializations() {
        viewModelScope.launch {
            kotlin.runCatching {
                getSpecializationUseCase()
            }.fold(
                onSuccess = {
                    _specializations.value = it
                },
                onFailure = { Log.d("_specializations", it.message ?: "Error") }
            )
        }
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

    suspend fun createChat(request: CreateChatRequest): Int? {
        return chatUseCases.createChat(request)
    }

    suspend fun getChatsByUserId(userId: Int): List<ChatDTO> {
        return chatUseCases.getChatsByUserId(userId)
    }

    fun getMessagesByChatId(chatId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                chatUseCases.getMessagesByChatId(chatId)
            }.fold(
                onSuccess = { _messages.value = it },
                onFailure = { Log.d("userAuth", it.message ?: "Error") }
            )
        }
    }

    suspend fun sendMessage(request: SendMessageRequest): Boolean {
        val isSend = chatUseCases.sendMessage(request)
        getMessagesByChatId(request.chatId)
        return isSend
    }

    fun getFullChatsByUserId(role: String, userId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                chatUseCases.getFullChatsByUserId(role, userId)
            }.fold(
                onSuccess = { _fullChatsByUserId.value = it },
                onFailure = { Log.d("fullChatsByUserId", it.message ?: "Error") }
            )
        }
    }

    fun loadTokenFromPrefs(context: Context) {
        val savedToken = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getString("token", "") ?: ""
        _token.value = savedToken
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