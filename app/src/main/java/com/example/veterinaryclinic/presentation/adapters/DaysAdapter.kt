package com.example.veterinaryclinic.presentation.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.data.models.treatment.DayItem
import com.example.veterinaryclinic.data.models.treatment.DayState
import com.example.veterinaryclinic.databinding.ItemDayBinding

class DaysAdapter :
    ListAdapter<DayItem, DaysAdapter.ItemDayViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDayViewHolder {
        return ItemDayViewHolder(
            ItemDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemDayViewHolder, position: Int) {
        val day = getItem(position)
        with(holder.binding) {
            dayNumber.text = day.dayNumber.toString()
            dayWeek.text = day.dayOfWeek
            when (day.state) {
                DayState.AVAILABLE -> {
                    dayStatus.setImageResource(R.drawable.ic_check_green)
                    dayStatus.visibility = View.VISIBLE
                }
                DayState.UNAVAILABLE -> {
                    dayStatus.setImageResource(R.drawable.ic_missing_item)
                    dayStatus.visibility = View.VISIBLE
                }
                DayState.SELECTED -> {
                    root.setBackgroundResource(R.drawable.bg_day_selected)
                }
                DayState.DEFAULT -> {
                    dayStatus.setImageResource(R.drawable.ic_item_in_process)
                    root.setBackgroundResource(R.drawable.bg_day_default)
                }

            }
        }

    }


    class ItemDayViewHolder(val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<DayItem>() {
        override fun areItemsTheSame(
            oldItem: DayItem,
            newItem: DayItem
        ) = oldItem.dayNumber == newItem.dayNumber

        override fun areContentsTheSame(
            oldItem: DayItem,
            newItem: DayItem
        ) = oldItem == newItem
    }
}