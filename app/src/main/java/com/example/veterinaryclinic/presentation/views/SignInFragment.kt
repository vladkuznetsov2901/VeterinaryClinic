package com.example.veterinaryclinic.presentation.views

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
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.databinding.FragmentSignInBinding
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue
import androidx.core.content.edit
import com.example.veterinaryclinic.data.models.users.AuthResult

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = context?.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        binding.signInButton.setOnClickListener {
            val emailOrPhone = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString()

            lifecycleScope.launch {
                viewModel.userAuth(emailOrPhone, password)

                viewModel.authResult.collect { result ->
                    when (result) {
                        is AuthResult.Success -> {
                            sharedPreferences?.edit {
                                putString("token", result.token)
                                putString("role", "user")
                            }
                            if (findNavController().currentDestination?.id == R.id.authFragment) {
                                findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                            }
                        }
                        is AuthResult.Error -> {
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {} // do nothing
                    }
                }
            }


        }

        binding.signInDoctorButton.setOnClickListener {
            val emailOrPhone = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString()

            lifecycleScope.launch {
                viewModel.doctorAuth(emailOrPhone, password)

                viewModel.authResult.collect { result ->
                    when (result) {
                        is AuthResult.Success -> {
                            sharedPreferences?.edit {
                                putString("token", result.token)
                                putString("role", "doctor")

                            }
                            if (findNavController().currentDestination?.id == R.id.authFragment) {
                                findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                            }
                        }
                        is AuthResult.Error -> {
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {} // do nothing
                    }
                }
            }


        }

        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_forgotPasswordFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}