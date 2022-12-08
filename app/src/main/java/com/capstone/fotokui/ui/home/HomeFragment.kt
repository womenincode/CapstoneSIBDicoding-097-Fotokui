package com.capstone.fotokui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.fotokui.databinding.FragmentHomeBinding
import com.capstone.fotokui.utils.DataDummy

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var epoxyHomeController: EpoxyHomeController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        epoxyHomeController = EpoxyHomeController()

        binding.epoxyHome.setController(epoxyHomeController)

        epoxyHomeController.setData(DataDummy.generatePhotographerPromos())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}