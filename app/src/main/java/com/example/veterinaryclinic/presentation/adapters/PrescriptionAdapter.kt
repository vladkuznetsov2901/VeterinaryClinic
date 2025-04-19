package com.example.veterinaryclinic.presentation.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.data.models.treatment.PrescriptionItemWithMedicationDto
import com.example.veterinaryclinic.databinding.ItemMedicineBinding

class PrescriptionAdapter :
    ListAdapter<PrescriptionItemWithMedicationDto, PrescriptionAdapter.ItemMedicineViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMedicineViewHolder {
        return ItemMedicineViewHolder(
            ItemMedicineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemMedicineViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            medicineName.text = item.medicationName
            medicineInstruction.text = item.instruction

            item.imageUrl?.let {
                Glide.with(medicineImage.context)
                    .load(it)
                    .placeholder(R.drawable.ic_medicine_placeholder)
                    .into(medicineImage)
            }

            val nextPlannedTime = item.schedule
                .firstOrNull { !it.isTaken }
                ?.plannedTime

            medicinePlanned.text = nextPlannedTime?.let { "Принять в $it" } ?: "Все приёмы завершены"

            val timesAdapter = TimeAdapter()
            medicineTimesRecycler.adapter = timesAdapter
            timesAdapter.submitList(item.schedule)
        }
    }



    class ItemMedicineViewHolder(val binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<PrescriptionItemWithMedicationDto>() {
        override fun areItemsTheSame(
            oldItem: PrescriptionItemWithMedicationDto,
            newItem: PrescriptionItemWithMedicationDto
        ) = oldItem.medicationName == newItem.medicationName

        override fun areContentsTheSame(
            oldItem: PrescriptionItemWithMedicationDto,
            newItem: PrescriptionItemWithMedicationDto
        ) = oldItem == newItem
    }
}