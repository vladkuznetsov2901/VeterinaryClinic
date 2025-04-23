package com.example.veterinaryclinic.presentation.views.treatment

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.data.models.treatment.MedicationScheduleDto
import com.example.veterinaryclinic.databinding.DialogMedicineDetailsBinding
import com.example.veterinaryclinic.databinding.FragmentTreatmentBinding
import com.example.veterinaryclinic.databinding.TimeNotificationsSetDialogBinding
import com.example.veterinaryclinic.presentation.adapters.TimeAdapter
import com.example.veterinaryclinic.presentation.viewmodels.TreatmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SetTimeNotificationsDialogFragment : DialogFragment() {
    private var _binding: TimeNotificationsSetDialogBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: TreatmentViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TimeNotificationsSetDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.timePicker.setIs24HourView(true)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}