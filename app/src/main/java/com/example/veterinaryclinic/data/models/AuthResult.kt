package com.example.veterinaryclinic.data.models

sealed class AuthResult {
    data class Success(val token: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}