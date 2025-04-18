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
import com.example.veterinaryclinic.databinding.FragmentCodeBinding
import com.example.veterinaryclinic.databinding.FragmentForgotPasswordBinding
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModel
import com.example.veterinaryclinic.presentation.viewmodels.RestorePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CodeFragment : Fragment() {


    private var _binding: FragmentCodeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: RestorePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCodeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()

        val userEmail = arguments?.getString("user_email")

        binding.sendButton.setOnClickListener {
            val code = binding.codeInput.text.toString()
            bundle.putString("user_email", userEmail)
            bundle.putString("user_code", code)
            userEmail?.let { it1 -> viewModel.verifyCode(it1, code) }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is RestorePasswordViewModel.UiState.CodeVerified -> {
                        findNavController().navigate(
                            R.id.action_codeFragment_to_newPasswordFragment,
                            bundle
                        )
                        viewModel.resetState()
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