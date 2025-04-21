package com.example.veterinaryclinic.presentation.views.treatment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.data.models.treatment.MedicationScheduleDto
import com.example.veterinaryclinic.databinding.DialogMedicineDetailsBinding
import com.example.veterinaryclinic.databinding.FragmentTreatmentBinding
import com.example.veterinaryclinic.presentation.adapters.TimeAdapter
import com.example.veterinaryclinic.presentation.viewmodels.TreatmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MedicineDialogFragment : DialogFragment() {
    private var _binding: DialogMedicineDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: TreatmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogMedicineDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val petId = sharedPreferences?.getInt("petId", 0)


        var scheduleItemSaved: MedicationScheduleDto? = null

        val timesAdapter = TimeAdapter()
        binding.scheduleRecycler.adapter = timesAdapter


        viewModel.selectedMedicine.observe(viewLifecycleOwner) { prescriptionItem ->

            prescriptionItem.imageUrl?.let {
                Glide.with(binding.medicineImage.context)
                    .load(it)
                    .placeholder(R.drawable.ic_medicine_placeholder)
                    .into(binding.medicineImage)
            }

            binding.medicineName.text = getString(
                R.string.medication_name_dialog_txt,
                prescriptionItem.medicationName,
                prescriptionItem.medicationDosage
            )

            binding.instructionText.text = prescriptionItem.frequency
            binding.dosageText.text = prescriptionItem.dosage
            binding.moreInstructionText.text = prescriptionItem.instruction
            binding.durationText.text =
                getString(R.string.duration_course_txt, prescriptionItem.durationDays)
            timesAdapter.submitList(prescriptionItem.schedule)

        }


        binding.closeButton.setOnClickListener {
            dismiss() // Close the dialog
        }

        timesAdapter.onDayClick = { scheduleItem ->
            scheduleItemSaved = scheduleItem
            Log.d("scheduleItem.scheduleId", "onViewCreated: ${scheduleItem.scheduleId}")
        }

        binding.btnAccept.setOnClickListener {
            if (scheduleItemSaved != null) {
                viewModel.markScheduleAsTaken(scheduleItemSaved!!.scheduleId) {
                    val currentList = timesAdapter.currentList.toMutableList()
                    val index =
                        currentList.indexOfFirst { it.scheduleId == scheduleItemSaved!!.scheduleId }
                    if (index != -1) {
                        val updatedItem = scheduleItemSaved!!.copy(isTaken = true)
                        currentList[index] = updatedItem
                        timesAdapter.submitList(currentList.toList())
                        if (petId != null) {
                            viewModel.loadPrescriptionByPetId(petId)
                            timesAdapter.notifyDataSetChanged()
                            dismiss()
                        }
                    }
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            if (scheduleItemSaved != null) {
                viewModel.markScheduleAsTaken(scheduleItemSaved!!.scheduleId) {
                    val currentList = timesAdapter.currentList.toMutableList()
                    val index =
                        currentList.indexOfFirst { it.scheduleId == scheduleItemSaved!!.scheduleId }
                    if (index != -1) {
                        val updatedItem = scheduleItemSaved!!.copy(isTaken = false)
                        currentList[index] = updatedItem
                        timesAdapter.submitList(currentList.toList())
                        if (petId != null) {
                            viewModel.loadPrescriptionByPetId(petId)
                            timesAdapter.notifyDataSetChanged()
                            dismiss()
                        }
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