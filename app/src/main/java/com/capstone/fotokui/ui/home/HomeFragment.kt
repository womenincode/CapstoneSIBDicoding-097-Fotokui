package com.capstone.fotokui.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.fotokui.databinding.FragmentHomeBinding
import com.capstone.fotokui.ui.auth.AuthActivity
import com.capstone.fotokui.ui.auth.AuthViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

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
        }

        epoxyHomeController = EpoxyHomeController()

        binding.epoxyHome.setController(epoxyHomeController)

        observeHomeScreenUiState()

        val firebaseFireStore = Firebase.firestore

        firebaseFireStore.collection("users").document(authViewModel.currentUser?.uid.toString())
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
}