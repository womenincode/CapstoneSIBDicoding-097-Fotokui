package com.capstone.fotokui.ui.findphotographer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.fotokui.databinding.FragmentFindPhotographerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPhotographerFragment : Fragment() {

    private var _binding: FragmentFindPhotographerBinding? = null
    private val binding get() = _binding!!

    private val findPhotographerViewModel: FindPhotographerViewModel by viewModels()

    private lateinit var epoxyFindPhotographerController: EpoxyFindPhotographerController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFindPhotographerBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            findPhotographerViewModel.getFindPhotographerScreenUiState()
        }

        epoxyFindPhotographerController = EpoxyFindPhotographerController(requireContext())

        binding.epoxyFindPhotographer.setController(epoxyFindPhotographerController)

        observeFindPhotograherScreenUiState()
    }

    private fun observeFindPhotograherScreenUiState() {
        findPhotographerViewModel.findPhotographerScreenUiState.observe(viewLifecycleOwner) { findPhotographerScreenUiState ->
            epoxyFindPhotographerController.setData(findPhotographerScreenUiState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}