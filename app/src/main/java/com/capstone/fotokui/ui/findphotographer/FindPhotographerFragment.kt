package com.capstone.fotokui.ui.findphotographer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.fotokui.databinding.FragmentFindPhotographerBinding
import com.capstone.fotokui.ui.detailphotographer.DetailPhotographerActivity
import com.capstone.fotokui.ui.home.EpoxyHomeController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPhotographerFragment : Fragment(), EpoxyHomeController.OnPhotographerListener {

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

        epoxyFindPhotographerController = EpoxyFindPhotographerController(requireContext(), this) { text, _, _, count ->
            if (count > 0) {
                findPhotographerViewModel.searchPhotographer(text.toString())
            } else {
                findPhotographerViewModel.getFindPhotographerScreenUiState()
            }
        }

        binding.epoxyFindPhotographer.setController(epoxyFindPhotographerController)

        observeFindPhotographerScreenUiState()
    }

    private fun observeFindPhotographerScreenUiState() {
        findPhotographerViewModel.findPhotographerScreenUiState.observe(viewLifecycleOwner) { findPhotographerScreenUiState ->
            epoxyFindPhotographerController.setData(findPhotographerScreenUiState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(photographerId: String) {
        val detailPhotographerIntent = Intent(activity, DetailPhotographerActivity::class.java).apply {
            putExtra(DetailPhotographerActivity.EXTRA_PHOTOGRAPHER_ID, photographerId)
        }
        startActivity(detailPhotographerIntent)
    }

    override fun onFavoriteClick(id: String) {
        Toast.makeText(activity, "Ditambahkan ke favorite", Toast.LENGTH_SHORT).show()
    }
}