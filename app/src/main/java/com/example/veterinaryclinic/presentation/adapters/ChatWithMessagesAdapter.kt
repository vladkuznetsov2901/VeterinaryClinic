package com.example.veterinaryclinic.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.data.models.DoctorWithSpecializationDTO
import com.example.veterinaryclinic.data.models.Promo
import com.example.veterinaryclinic.data.models.chats.MessageDTO
import com.example.veterinaryclinic.databinding.SampleDoctorItemBinding
import com.example.veterinaryclinic.databinding.SampleDoctorMessageItemBinding
import com.example.veterinaryclinic.databinding.SamplePromoImageItemBinding
import com.example.veterinaryclinic.databinding.SampleUserMessageItemBinding
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class ChatWithMessagesAdapter(private val currentUserRole: String) :
    ListAdapter<MessageDTO, RecyclerView.ViewHolder>(DiffCallback()) {


    companion object {
        private const val VIEW_TYPE_USER = 0
        private const val VIEW_TYPE_DOCTOR = 1
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.senderType == currentUserRole) {
            VIEW_TYPE_USER
        } else {
            VIEW_TYPE_DOCTOR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val binding = SampleUserMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                UserMessageViewHolder(binding)
            }
            VIEW_TYPE_DOCTOR -> {
                val binding = SampleDoctorMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                DoctorMessageViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder) {
            is UserMessageViewHolder -> holder.bind(message)
            is DoctorMessageViewHolder -> holder.bind(message)
        }
    }

    class UserMessageViewHolder(private val binding: SampleUserMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageDTO) {
            binding.userMessage.text = message.messageText
            binding.userTime.text = message.sentAt
        }
    }

    class DoctorMessageViewHolder(private val binding: SampleDoctorMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageDTO) {
            binding.doctorMessage.text = message.messageText
            binding.doctorTime.text = message.sentAt
        }
    }

    class MessagesViewHolder(val binding: SampleDoctorItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<MessageDTO>() {
        override fun areItemsTheSame(
            oldItem: MessageDTO,
            newItem: MessageDTO
        ) = oldItem.chatId == newItem.chatId

        override fun areContentsTheSame(
            oldItem: MessageDTO,
            newItem: MessageDTO
        ) = oldItem == newItem
    }
}