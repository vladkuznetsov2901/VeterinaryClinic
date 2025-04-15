package com.example.veterinaryclinic.presentation.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinaryclinic.databinding.FragmentHomeBinding
import com.example.veterinaryclinic.presentation.adapters.DoctorsAdapter
import com.example.veterinaryclinic.presentation.adapters.NonScrollableLinearLayoutManager
import com.example.veterinaryclinic.presentation.adapters.PromoAdapter
import com.example.veterinaryclinic.presentation.adapters.SpecializationAdapter
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModel
import com.example.veterinaryclinic.presentation.viewmodels.MainViewModelFactory
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadHomeDataOnce()

        val promoAdapter = PromoAdapter()

        val specializationAdapter = SpecializationAdapter()

        val doctorsAdapter = DoctorsAdapter()

        binding.recyclerPromo.adapter = promoAdapter
        binding.recyclerSpecialization.adapter = specializationAdapter


        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
            flexWrap = FlexWrap.WRAP
        }
        binding.recyclerSpecialization.layoutManager = layoutManager

        binding.recyclerDoctors.layoutManager = NonScrollableLinearLayoutManager(context)
        binding.recyclerDoctors.isNestedScrollingEnabled = false
        binding.recyclerDoctors.adapter = doctorsAdapter


        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.promoImages.collect { promoImages ->
                    promoAdapter.submitList(promoImages)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.specializations.collect { specializations ->
                    specializationAdapter.submitList(specializations)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.doctors.collect { doctors ->
                    doctorsAdapter.submitList(doctors)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


