package com.example.veterinaryclinic.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.data.models.SpecializationDTO
import com.example.veterinaryclinic.databinding.SamplePromoImageItemBinding
import com.example.veterinaryclinic.databinding.SampleSpecializationItemBinding

class SpecializationAdapter :
    ListAdapter<SpecializationDTO, SpecializationAdapter.SpecializationViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecializationViewHolder {
        return SpecializationViewHolder(
            SampleSpecializationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SpecializationViewHolder, position: Int) {
        val specializationTitle = getItem(position)
        with(holder.binding) {
            specializationText.text = specializationTitle.title
        }
    }

    class SpecializationViewHolder(val binding: SampleSpecializationItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<SpecializationDTO>() {
        override fun areItemsTheSame(oldItem: SpecializationDTO, newItem: SpecializationDTO) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SpecializationDTO, newItem: SpecializationDTO) =
            oldItem == newItem
    }
}