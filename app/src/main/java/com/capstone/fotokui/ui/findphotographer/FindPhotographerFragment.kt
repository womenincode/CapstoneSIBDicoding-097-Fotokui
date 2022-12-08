package com.capstone.fotokui.ui.findphotographer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.fotokui.databinding.FragmentFindPhotographerBinding
import com.capstone.fotokui.utils.DataDummy

class FindPhotographerFragment : Fragment() {

    private var _binding: FragmentFindPhotographerBinding? = null
    private val binding get() = _binding!!

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

        epoxyFindPhotographerController = EpoxyFindPhotographerController(requireContext())

        binding.epoxyFindPhotographer.setController(epoxyFindPhotographerController)

        epoxyFindPhotographerController.setData(DataDummy.generatePhotographers(30))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}