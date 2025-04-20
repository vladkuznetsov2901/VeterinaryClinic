package com.example.veterinaryclinic.data.models.treatment

import java.time.LocalDate

data class DayItem(
    val dayNumber: Int,
    val dayOfWeek: String,
    val date: LocalDate,
    val state: DayState // см. ниже
)
