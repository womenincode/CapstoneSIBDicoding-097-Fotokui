package com.capstone.fotokui.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.fotokui.databinding.FragmentHomeBinding
import com.capstone.fotokui.ui.auth.AuthActivity
import com.capstone.fotokui.ui.auth.AuthViewModel
import com.capstone.fotokui.ui.detailphotographer.DetailPhotographerActivity
import com.capstone.fotokui.ui.editprofile.EditProfileActivity
import com.capstone.fotokui.ui.maps.MapsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), EpoxyHomeController.OnPhotographerListener,
    EpoxyHomeController.OnHomeClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

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

        if (authViewModel.currentUser == null) {
            navigateToAuthScreen()
        }

        if (savedInstanceState == null) {
            homeViewModel.getCurrentUser()
            homeViewModel.getPromotionPhotographers()
        }

        epoxyHomeController = EpoxyHomeController(this, this)

        binding.epoxyHome.setController(epoxyHomeController)

        observeHomeScreenUiState()
    }

    private fun observeHomeScreenUiState() {
        homeViewModel.homeScreenUiState.observe(viewLifecycleOwner) { homeScreenUiState ->
            epoxyHomeController.setData(homeScreenUiState)
        }
    }

    private fun navigateToAuthScreen() {
        val authIntent = Intent(context, AuthActivity::class.java)
        startActivity(authIntent)
        activity?.finish()
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

    override fun onMapClick() {
        val mapsIntent = Intent(activity, MapsActivity::class.java)
        startActivity(mapsIntent)
    }

    override fun onProfileClick() {
        val authIntent = Intent(context, EditProfileActivity::class.java)
        startActivity(authIntent)
    }
}