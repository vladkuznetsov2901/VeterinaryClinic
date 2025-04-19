package com.example.veterinaryclinic.data.models.treatment

data class DayItem(
    val dayNumber: Int,
    val dayOfWeek: String,
    val state: DayState // см. ниже
)
