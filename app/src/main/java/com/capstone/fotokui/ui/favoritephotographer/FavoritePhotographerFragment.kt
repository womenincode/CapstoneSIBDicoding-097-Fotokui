package com.capstone.fotokui.ui.favoritephotographer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.fotokui.databinding.FragmentFavoritePhotographerBinding
import com.capstone.fotokui.ui.auth.AuthViewModel
import com.capstone.fotokui.ui.detailphotographer.DetailPhotographerActivity
import com.capstone.fotokui.ui.home.EpoxyHomeController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePhotographerFragment : Fragment(), EpoxyHomeController.OnPhotographerListener {

    private var _binding: FragmentFavoritePhotographerBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()
    private val favoritePhotographerViewModel: FavoritePhotographerViewModel by viewModels()

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

        val currentUser = authViewModel.currentUser

        favoritePhotographerViewModel.getFavoritePhotographerScreenUiState(currentUser?.uid.toString())

        epoxyFavoritePhotographerController = EpoxyFavoritePhotographerController(requireContext(), this)

        binding.epoxyFavoritePhotographer.setController(epoxyFavoritePhotographerController)

        observeFavoritePhotographerScreenUiState()
    }

    private fun observeFavoritePhotographerScreenUiState() {
        favoritePhotographerViewModel.favoritePhotographerScreenUiState.observe(viewLifecycleOwner) { favoritePhotographerScreenUiState ->
            epoxyFavoritePhotographerController.setData(favoritePhotographerScreenUiState)
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