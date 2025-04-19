package com.example.veterinaryclinic.presentation.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.data.models.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.databinding.SampleDoctorItemBinding
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class DoctorsAdapter :
    ListAdapter<DoctorWithSpecializationDTO, DoctorsAdapter.DoctorViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        return DoctorViewHolder(
            SampleDoctorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = getItem(position)
        with(holder.binding) {
            textViewDoctorName.text = "${doctor.surname} ${doctor.name} ${doctor.lastname}"
            textViewSpecialization.text = doctor.specialization
            textViewDoctorRate.text = doctor.rate.toString()
            textViewExperience.text = "Стаж ${calculateDoctorExperience(doctor.startWorkDate)} лет"
            textViewPrice.text = "500 руб."

            imageViewDoctor.setImageResource(R.drawable.doctor_ic)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateDoctorExperience(startDateString: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val startDate = LocalDate.parse(startDateString, formatter)
        val today = LocalDate.now()
        val experience = Period.between(startDate, today)
        return experience.years
    }

    class DoctorViewHolder(val binding: SampleDoctorItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<DoctorWithSpecializationDTO>() {
        override fun areItemsTheSame(
            oldItem: DoctorWithSpecializationDTO,
            newItem: DoctorWithSpecializationDTO
        ) = oldItem.doctorId == newItem.doctorId

        override fun areContentsTheSame(
            oldItem: DoctorWithSpecializationDTO,
            newItem: DoctorWithSpecializationDTO
        ) = oldItem == newItem
    }
}