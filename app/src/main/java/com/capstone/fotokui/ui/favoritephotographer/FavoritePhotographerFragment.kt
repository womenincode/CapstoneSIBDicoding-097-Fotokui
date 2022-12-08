package com.capstone.fotokui.ui.favoritephotographer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.fotokui.databinding.FragmentFavoritePhotographerBinding
import com.capstone.fotokui.utils.DataDummy

class FavoritePhotographerFragment : Fragment() {

    private var _binding: FragmentFavoritePhotographerBinding? = null
    private val binding get() = _binding!!

    private lateinit var epoxyFavoritePhotographerController: EpoxyFavoritePhotographerController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritePhotographerBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        epoxyFavoritePhotographerController = EpoxyFavoritePhotographerController(requireContext())

        binding.epoxyFavoritePhotographer.setController(epoxyFavoritePhotographerController)

        epoxyFavoritePhotographerController.setData(DataDummy.generateFavoritePhotographer(40))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}