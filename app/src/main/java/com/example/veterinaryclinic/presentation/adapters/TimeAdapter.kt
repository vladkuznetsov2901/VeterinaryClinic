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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimeAdapter : ListAdapter<MedicationScheduleDto, TimeAdapter.TimeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medicine_time, parent, false)
        return TimeViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(schedule: MedicationScheduleDto) {
            val timeTextView = itemView.findViewById<TextView>(R.id.timeText)

            timeTextView.text = formatPlannedTime(schedule.plannedTime)
            val context = itemView.context

            if (schedule.isTaken) {
                timeTextView.setTextColor(ContextCompat.getColor(context, R.color.green))
            } else {
                timeTextView.setTextColor(ContextCompat.getColor(context, R.color.gray))
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun formatPlannedTime(plannedTime: String): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
            val timeOnlyFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val dateTime = LocalDateTime.parse(plannedTime, formatter)
            return "Принять в ${dateTime.format(timeOnlyFormatter)}"
        }
    }



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


