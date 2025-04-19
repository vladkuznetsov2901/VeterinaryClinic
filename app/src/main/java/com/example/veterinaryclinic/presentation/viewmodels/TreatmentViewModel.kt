package com.example.veterinaryclinic.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaryclinic.data.models.treatment.DayItem
import com.example.veterinaryclinic.data.models.treatment.DayState
import com.example.veterinaryclinic.data.models.treatment.PetDto
import com.example.veterinaryclinic.data.models.treatment.PrescriptionDto
import com.example.veterinaryclinic.data.models.treatment.PrescriptionItemWithMedicationDto
import com.example.veterinaryclinic.domain.usecases.GetPrescriptionsForPetUseCase
import com.example.veterinaryclinic.domain.usecases.GetUserPetsUseCase
import com.example.veterinaryclinic.features.toMedicationDisplayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TreatmentViewModel @Inject constructor(
    private val getUserPetsUseCase: GetUserPetsUseCase,
    private val getPrescriptionsForPetUseCase: GetPrescriptionsForPetUseCase
) : ViewModel() {

    private val _pets = MutableStateFlow<List<PetDto>>(emptyList())
    val pets = _pets.asStateFlow()

    private val _prescriptions =
        MutableStateFlow<List<PrescriptionItemWithMedicationDto>>(emptyList())
    val prescriptions = _prescriptions.asStateFlow()

    fun loadPets(userId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                getUserPetsUseCase(userId)
            }.fold(
                onSuccess = {
                    _pets.value = it

                },
                onFailure = { Log.d("pets", it.message ?: "Error") }
            )
        }
    }

    fun loadPrescriptionByPetId(petId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                getPrescriptionsForPetUseCase(petId)
            }.fold(
                onSuccess = {
                    val medicationList = it.toMedicationDisplayList()
                    _prescriptions.value = medicationList

                },
                onFailure = { Log.d("prescriptions", it.message ?: "Error") }
            )
        }
    }


    fun generateDaysOfMonth(year: Int, month: Int): List<DayItem> {
        val list = mutableListOf<DayItem>()
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1)
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (day in 1..maxDay) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val dayOfWeek = SimpleDateFormat("EE", Locale("ru")).format(calendar.time)
            list.add(
                DayItem(
                    dayNumber = day,
                    dayOfWeek = dayOfWeek,
                    state = DayState.DEFAULT
                )
            )
        }
        return list
    }


}