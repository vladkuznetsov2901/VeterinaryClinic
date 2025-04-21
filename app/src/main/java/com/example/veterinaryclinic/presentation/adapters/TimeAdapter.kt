package com.example.veterinaryclinic.presentation.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.data.models.treatment.MedicationScheduleDto
import com.example.veterinaryclinic.databinding.ItemDayBinding
import com.example.veterinaryclinic.databinding.ItemMedicineBinding
import com.example.veterinaryclinic.databinding.ItemMedicineTimeBinding
import com.example.veterinaryclinic.databinding.SampleDoctorItemBinding
import com.example.veterinaryclinic.presentation.adapters.DaysAdapter.ItemDayViewHolder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimeAdapter : ListAdapter<MedicationScheduleDto, TimeAdapter.TimeViewHolder>(DiffCallback()) {

    var onDayClick: ((MedicationScheduleDto) -> Unit)? = null
    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        return TimeViewHolder(
            ItemMedicineTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {

            timeText.text = formatPlannedTime(item.plannedTime)

            if (position == selectedPosition) {
                timeText.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.black
                    )
                )
            } else {
                if (item.isTaken) {
                    checkIcon.visibility = View.VISIBLE
                    timeText.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.green
                        )
                    )
                } else {
                    timeText.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.gray
                        )
                    )
                }
            }

            root.setOnClickListener {
                selectedPosition = if (selectedPosition == position) {
                    -1
                } else {
                    position
                }
                notifyDataSetChanged()
                onDayClick?.invoke(item)
            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatPlannedTime(plannedTime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val timeOnlyFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val dateTime = LocalDateTime.parse(plannedTime, formatter)
        return "Принять в ${dateTime.format(timeOnlyFormatter)}"
    }


    class TimeViewHolder(val binding: ItemMedicineTimeBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<MedicationScheduleDto>() {
        override fun areItemsTheSame(
            oldItem: MedicationScheduleDto,
            newItem: MedicationScheduleDto
        ): Boolean {
            return oldItem.plannedTime == newItem.plannedTime
        }

        override fun areContentsTheSame(
            oldItem: MedicationScheduleDto,
            newItem: MedicationScheduleDto
        ): Boolean {
            return oldItem == newItem
        }
    }
}


