package com.example.veterinaryclinic.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.databinding.SamplePromoImageItemBinding

class PromoAdapter:
    ListAdapter<String, PromoAdapter.PromoViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        return PromoViewHolder(
            SamplePromoImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        val imageUrl = getItem(position)
        Glide.with(holder.binding.promoImage.context)
            .load(imageUrl)
            .into(holder.binding.promoImage)
    }

    class PromoViewHolder(val binding: SamplePromoImageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}