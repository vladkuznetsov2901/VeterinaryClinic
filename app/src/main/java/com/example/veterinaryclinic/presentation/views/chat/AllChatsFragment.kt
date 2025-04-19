package com.example.veterinaryclinic.presentation.views.chat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.databinding.FragmentAllChatsBinding
import com.example.veterinaryclinic.presentation.adapters.ChatsAdapter
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModel
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllChatsFragment : Fragment() {
    private var _binding: FragmentAllChatsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllChatsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        var userRole = sharedPreferences?.getString("role", "")

        viewModel.loadTokenFromPrefs(requireContext())

        val chatsAdapter = ChatsAdapter { chat ->
            val bundle = Bundle().apply {

                putInt("chat_id", chat.chatId)
                putInt("doctor_id", chat.doctorId)
                putString("doctor_name", chat.doctorName)
            }

            findNavController().navigate(R.id.action_allChatsFragment_to_chatFragment, bundle)

        }

        binding.recyclerChats.adapter = chatsAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.token.collectLatest { token ->
                    viewModel.getUserIdByToken(token)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userId.collectLatest { userId ->
                    viewModel.getFullChatsByUserId(userRole.toString(), userId)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fullChatsByUserId.collectLatest { fullChats ->
                    chatsAdapter.submitList(fullChats)
                }
            }
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
