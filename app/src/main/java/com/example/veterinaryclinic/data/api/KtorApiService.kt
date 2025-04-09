package com.example.veterinaryclinic.data.api


import com.example.veterinaryclinic.data.models.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.LoginData
import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.data.models.TokenResponse
import com.example.veterinaryclinic.data.models.RegistrationData
import com.example.veterinaryclinic.data.models.SpecializationDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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

}

val ktorRetrofit = Retrofit.Builder().client(
    OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }).build()
).baseUrl("http://10.0.2.2:8080/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val ktorApiService = ktorRetrofit.create(KtorApiService::class.java)

