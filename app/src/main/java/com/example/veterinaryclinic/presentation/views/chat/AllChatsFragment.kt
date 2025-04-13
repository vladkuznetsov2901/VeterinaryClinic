package com.example.veterinaryclinic.presentation.views.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.veterinaryclinic.R
import com.example.veterinaryclinic.databinding.FragmentAllChatsBinding
import com.example.veterinaryclinic.databinding.FragmentSignInBinding


class AllChatsFragment : Fragment() {
    private var _binding: FragmentAllChatsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllChatsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}