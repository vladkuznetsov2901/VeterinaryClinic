package com.example.veterinaryclinic.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.databinding.FragmentHomeBinding
import com.example.veterinaryclinic.databinding.FragmentMakeAnAppointmentBinding
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModel
import com.example.veterinaryclinic.presentation.viewmodels.MakeAnAppointmentViewModel


class MakeAnAppointmentFragment : Fragment() {

    private var _binding: FragmentMakeAnAppointmentBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MakeAnAppointmentViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMakeAnAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }


}