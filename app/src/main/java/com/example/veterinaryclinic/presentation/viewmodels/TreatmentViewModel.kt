package com.example.veterinaryclinic.presentation.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaryclinic.data.models.treatment.DayItem
import com.example.veterinaryclinic.data.models.treatment.DayState
import com.example.veterinaryclinic.data.models.treatment.MedicationDto
import com.example.veterinaryclinic.data.models.treatment.PetDto
import com.example.veterinaryclinic.data.models.treatment.PrescriptionItemWithMedicationDto
import com.example.veterinaryclinic.domain.usecases.notifications.ScheduleNotificationUseCase
import com.example.veterinaryclinic.domain.usecases.prescription.GetPrescriptionsForPetUseCase
import com.example.veterinaryclinic.domain.usecases.users.GetUserPetsUseCase
import com.example.veterinaryclinic.domain.usecases.prescription.MarkScheduleTakenUseCase
import com.example.veterinaryclinic.features.toMedicationDisplayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TreatmentViewModel @Inject constructor(
    private val getUserPetsUseCase: GetUserPetsUseCase,
    private val getPrescriptionsForPetUseCase: GetPrescriptionsForPetUseCase,
    private val markScheduleTakenUseCase: MarkScheduleTakenUseCase,
    private val scheduleNotificationUseCase: ScheduleNotificationUseCase
) : ViewModel() {

    private val _pets = MutableStateFlow<List<PetDto>>(emptyList())
    val pets = _pets.asStateFlow()

    private val _prescriptions =
        MutableStateFlow<List<PrescriptionItemWithMedicationDto>>(emptyList())
    val prescriptions = _prescriptions.asStateFlow()

    private val _filteredPrescriptions =
        MutableStateFlow<List<PrescriptionItemWithMedicationDto>>(emptyList())
    val filteredPrescriptions = _filteredPrescriptions.asStateFlow()

    val selectedDate = MutableLiveData<LocalDate>() // Хранит выбранную дату

    private val _days = MutableStateFlow<List<DayItem>>(emptyList())
    val days = _days.asStateFlow()

    val selectedMedicine = MutableLiveData<PrescriptionItemWithMedicationDto>()
    fun setMedicine(medicine: PrescriptionItemWithMedicationDto) {
        selectedMedicine.value = medicine
    }

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadPrescriptionByPetId(petId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                getPrescriptionsForPetUseCase(petId)
            }.fold(
                onSuccess = {
                    val medicationList = it.toMedicationDisplayList()
                    _prescriptions.value = medicationList
                    filterPrescriptionsBySelectedDate(medicationList)
                },
                onFailure = { Log.d("prescriptions", it.message ?: "Error") }
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
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
                    date = LocalDate.of(year, month, day),
                    state = DayState.DEFAULT
                )
            )
        }
        return list
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateSelected(date: LocalDate) {
        selectedDate.value = date
        val filtered = filterPrescriptionsByDate(prescriptions.value, date)
        _filteredPrescriptions.value = filtered

        val updatedDays =
            updateDayStates(generateDaysOfMonth(date.year, date.monthValue), prescriptions.value)
        _days.value = updatedDays
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun filterPrescriptionsByDate(
        prescriptions: List<PrescriptionItemWithMedicationDto>,
        date: LocalDate
    ): List<PrescriptionItemWithMedicationDto> {
        return prescriptions.mapNotNull { item ->
            val filteredSchedule = item.schedule.filter { schedule ->
                val dateTime = LocalDateTime.parse(schedule.plannedTime)
                dateTime.toLocalDate() == date
            }
            if (filteredSchedule.isNotEmpty()) {
                item.copy(schedule = filteredSchedule)
            } else null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDayStates(
        days: List<DayItem>,
        prescriptions: List<PrescriptionItemWithMedicationDto>
    ): List<DayItem> {
        return days.map { day ->
            val hasMedication = prescriptions.any { prescription ->
                prescription.schedule.any { schedule ->
                    LocalDateTime.parse(schedule.plannedTime).toLocalDate() == day.date
                }
            }
            day.copy(
                state = when {
                    selectedDate.value == day.date -> DayState.SELECTED
                    hasMedication -> DayState.IN_PROCESS
                    else -> DayState.DEFAULT
                }
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterPrescriptionsBySelectedDate(prescriptions: List<PrescriptionItemWithMedicationDto>) {
        selectedDate.value?.let { selected ->
            val filtered = filterPrescriptionsByDate(prescriptions, selected)
            _filteredPrescriptions.value = filtered
            scheduleNotificationsForPrescriptions(filtered)
        }
    }


    fun markScheduleAsTaken(scheduleId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val success = markScheduleTakenUseCase(scheduleId)
            if (success) onSuccess()
        }
    }

    private fun scheduleNotificationsForPrescriptions(prescriptions: List<PrescriptionItemWithMedicationDto>) {
        prescriptions.forEach { prescription ->
            prescription.schedule.forEach { medicationSchedule ->
                scheduleNotificationUseCase(medicationSchedule, prescription.medicationName)
            }
        }
    }


}
