package com.example.veterinaryclinic.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.databinding.FragmentForgotPasswordBinding
import com.example.veterinaryclinic.databinding.FragmentNewPasswordBinding
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModel
import com.example.veterinaryclinic.presentation.viewmodels.RestorePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewPasswordFragment : Fragment() {
    private var _binding: FragmentNewPasswordBinding? = null

    private val binding get() = _binding!!

    private val viewModel: RestorePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userEmail = arguments?.getString("user_email")
        val userCode = arguments?.getString("user_code")


        binding.sendButton.setOnClickListener {
            val pass = binding.newPasswordInput.text.toString()
            val confirm = binding.repeatPasswordInput.text.toString()
            if (userEmail != null && userCode != null) {
                viewModel.changePassword(pass, confirm, userEmail, userCode)
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is RestorePasswordViewModel.UiState.PasswordChanged -> {
                        Toast.makeText(requireContext(), "Пароль успешно изменён", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack(R.id.authFragment, false)
                    }
                    is RestorePasswordViewModel.UiState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}