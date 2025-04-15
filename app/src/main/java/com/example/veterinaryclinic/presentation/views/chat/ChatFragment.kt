package com.example.veterinaryclinic.presentation.views.chat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.data.models.chats.SendMessageRequest
import com.example.veterinaryclinic.databinding.FragmentChatBinding
import com.example.veterinaryclinic.databinding.FragmentHomeBinding
import com.example.veterinaryclinic.presentation.adapters.ChatWithMessagesAdapter
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import kotlin.getValue

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = context?.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        val chatId = arguments?.getInt("chat_id")
        val doctorName = arguments?.getString("doctor_name")?.substringBefore("(")
        var userRole = sharedPreferences?.getString("role", "")

        if (doctorName != null) {
            if (doctorName.isNotEmpty()) binding.doctorChatName.text = doctorName
        }

        val chatWithMessagesAdapter = ChatWithMessagesAdapter(userRole.toString())

        binding.recyclerMessages.adapter = chatWithMessagesAdapter

        chatWithMessagesAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.recyclerMessages.scrollToPosition(chatWithMessagesAdapter.itemCount - 1)
            }
        })

        if (chatId != null) {
            lifecycleScope.launch {
                viewModel.getMessagesByChatId(chatId)
                viewModel.messages.collect { messages ->
                    chatWithMessagesAdapter.submitList(messages)
                }
            }
        }

        binding.sendMessageBtn.setOnClickListener {
            if (binding.editMessageText.text.isBlank()) Toast.makeText(
                requireContext(), "Message is empty!",
                Toast.LENGTH_SHORT
            ).show()
            else {
                val message = binding.editMessageText.text.toString()
                if (chatId != null) {
                    lifecycleScope.launch {
                        viewModel.sendMessage(
                            SendMessageRequest(
                                chatId = chatId,
                                messageText = message,
                                senderType = userRole.toString()
                            )
                        )
                    }
                    binding.editMessageText.text.clear()

                }

            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}