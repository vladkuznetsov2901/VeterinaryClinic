package com.example.veterinaryclinic.data.api


import com.example.veterinaryclinic.data.models.ChatDTO
import com.example.veterinaryclinic.data.models.CreateChatRequest
import com.example.veterinaryclinic.data.models.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.LoginData
import com.example.veterinaryclinic.data.models.MessageDTO
import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.data.models.TokenResponse
import com.example.veterinaryclinic.data.models.RegistrationData
import com.example.veterinaryclinic.data.models.SendMessageRequest
import com.example.veterinaryclinic.data.models.SpecializationDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface KtorApiService {
    @POST("/register")
    suspend fun registerUser(
        @Body registrationData: RegistrationData,
    ): TokenResponse

    @POST("/login")
    suspend fun loginUser(
        @Body loginData: LoginData,
    ): TokenResponse

    @GET("/promo_images")
    suspend fun getPromoImages(): List<String>

    @GET("/specializations")
    suspend fun getSpecializations(): List<SpecializationDTO>

    @GET("/doctors")
    suspend fun getDoctors(): List<DoctorWithSpecializationDTO>

    @GET("/chats/{userId}")
    suspend fun getChatsByUserId(
        @Path("userId") userId: Int
    ): List<ChatDTO>

    @GET("/chats/messages/{chatId}")
    suspend fun getMessagesByChatId(
        @Path("chatId") chatId: Int
    ): List<MessageDTO>

    @POST("/chats/send_message")
    suspend fun sendMessage(
        @Body request: SendMessageRequest
    ): Response<Unit>

    @POST("/chats/create_chat")
    suspend fun createChat(
        @Body request: CreateChatRequest
    ): Response<Map<String, Int>>

}

val ktorRetrofit = Retrofit.Builder().client(
    OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }).build()
).baseUrl("http://10.0.2.2:8080/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val ktorApiService = ktorRetrofit.create(KtorApiService::class.java)

