package com.example.veterinaryclinic.presentation.views.treatment

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.veterinaryclinic.databinding.FragmentTreatmentBinding
import com.example.veterinaryclinic.presentation.adapters.DaysAdapter
import com.example.veterinaryclinic.presentation.adapters.PrescriptionAdapter
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModel
import com.example.veterinaryclinic.presentation.viewmodels.TreatmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

@AndroidEntryPoint
class TreatmentFragment : Fragment() {


    private var _binding: FragmentTreatmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: TreatmentViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTreatmentBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = context?.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        val daysAdapter = DaysAdapter()
        val prescriptionAdapter = PrescriptionAdapter()

        binding.daysRecycler.adapter = daysAdapter
        binding.medicinesRecycler.adapter = prescriptionAdapter

        val today = LocalDate.now()
        viewModel.onDateSelected(today)
        binding.profileDate.text = "${today.dayOfMonth}.${today.monthValue}.${today.year}"




        daysAdapter.onDayClick = { selectedDate ->
            viewModel.onDateSelected(selectedDate)
            binding.profileDate.text =
                "${selectedDate.dayOfMonth}.${selectedDate.monthValue}.${selectedDate.year}"
        }

        prescriptionAdapter.onItemClick = { prescriptionItem ->
            viewModel.setMedicine(prescriptionItem)

            val dialog = MedicineDialogFragment()
            dialog.show(parentFragmentManager, "MedicineDialog")
        }


        var isFirstLoad = true

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.days.collectLatest { dayItems ->
                    daysAdapter.submitList(dayItems) {
                        if (isFirstLoad) {
                            val todayIndex = dayItems.indexOfFirst { it.date == today }
                            if (todayIndex != -1) {
                                binding.daysRecycler.scrollToPosition(todayIndex - 4)
                                viewModel.onDateSelected(today)
                            }
                            isFirstLoad = false
                        }
                    }
                }
            }
        }



        binding.profileDate.text = getCurrentDate()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (sharedPreferences != null) {
                    sharedPreferences.getString("token", "")
                        ?.let { mainViewModel.getUserIdByToken(it) }
                    mainViewModel.userId.collectLatest { userId ->
                        viewModel.loadPets(userId)
                    }
                }

            }
        }


        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pets.collect { pets ->
                    pets.firstOrNull()?.let { pet ->
                        viewModel.loadPrescriptionByPetId(pet.id)
                        sharedPreferences?.edit {
                            putInt("petId", pet.id)
                        }
                        binding.profileName.text = pet.name

                    }
                }

            }
        }


        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredPrescriptions.collect { prescriptions ->
                    prescriptionAdapter.submitList(prescriptions)

                }
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCurrentDate(): String {
        return "${Calendar.getInstance().get(Calendar.DAY_OF_MONTH)}.${
            Calendar.getInstance().get(Calendar.MONTH) + 1
        }.${Calendar.getInstance().get(Calendar.YEAR)}"
    }

}