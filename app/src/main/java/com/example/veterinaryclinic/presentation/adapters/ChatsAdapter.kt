package com.example.veterinaryclinic.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinaryclinic.data.models.chats.ChatDTO
import com.example.veterinaryclinic.data.models.chats.FullChatDTO
import com.example.veterinaryclinic.databinding.SampleAllChatsItemBinding

class ChatsAdapter(private val onChatClick: (FullChatDTO) -> Unit) :
    ListAdapter<FullChatDTO, ChatsAdapter.ChatsViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        return ChatsViewHolder(
            SampleAllChatsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val chat = getItem(position)
        with(holder.binding) {
            textName.text = chat.doctorName
            textLastMessage.text = chat.lastMessage
            textTime.text = chat.lastMessageTime?.substring(0, 10)

            root.setOnClickListener {
                onChatClick(chat)
            }
        }

    }



    class ChatsViewHolder(val binding: SampleAllChatsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<FullChatDTO>() {
        override fun areItemsTheSame(
            oldItem: FullChatDTO,
            newItem: FullChatDTO
        ) = oldItem.chatId == newItem.chatId

        override fun areContentsTheSame(
            oldItem: FullChatDTO,
            newItem: FullChatDTO
        ) = oldItem == newItem
    }
}