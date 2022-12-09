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
import com.capstone.fotokui.utils.DataDummy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

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

        epoxyHomeController = EpoxyHomeController()

        binding.epoxyHome.setController(epoxyHomeController)

        epoxyHomeController.setData(DataDummy.generatePhotographerPromos(30))
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