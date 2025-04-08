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
import com.example.veterinaryclinic.databinding.FragmentSignInBinding
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModel
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val viewModel: MainViewModel by viewModels { mainViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {

            val emailOrPhone = binding.emailOrPhoneInput.text.toString().trim()
            val password = binding.passwordInput.text.toString()

            lifecycleScope.launch {
                viewModel.userAuth(emailOrPhone, password)
                viewModel.token.collect { token ->
                    if (token.isEmpty()) Toast.makeText(
                        requireContext(), "User auth failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                    else {
                        Toast.makeText(
                            requireContext(), "User auth success!",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                    }
                }
            }


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}