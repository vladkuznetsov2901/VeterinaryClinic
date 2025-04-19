package com.example.veterinaryclinic.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.data.models.treatment.MedicationScheduleDto

class TimeAdapter : ListAdapter<MedicationScheduleDto, TimeAdapter.TimeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medicine_time, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(schedule: MedicationScheduleDto) {
            val timeTextView = itemView.findViewById<TextView>(R.id.timeText)

            timeTextView.text = schedule.plannedTime
            val context = itemView.context

            if (schedule.isTaken) {
                timeTextView.setTextColor(ContextCompat.getColor(context, R.color.green))
            } else {
                timeTextView.setTextColor(ContextCompat.getColor(context, R.color.gray))
            }
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


