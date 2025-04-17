package com.example.veterinaryclinic.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.data.NetworkMonitor
import com.example.veterinaryclinic.databinding.FragmentNoConnectionBinding
import com.example.veterinaryclinic.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NoConnectionFragment : Fragment() {

    private var _binding: FragmentNoConnectionBinding? = null

    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoConnectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.retryConnectionBtn.setOnClickListener {
            lifecycleScope.launch {
                if (networkMonitor.isConnected.first()) {
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(), "Интернет всё ещё недоступен", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}