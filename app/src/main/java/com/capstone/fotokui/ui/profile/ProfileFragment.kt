package com.capstone.fotokui.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.FragmentProfileBinding
import com.capstone.fotokui.domain.ProfileActivity
import com.capstone.fotokui.ui.auth.AuthActivity
import com.capstone.fotokui.ui.auth.AuthViewModel
import com.capstone.fotokui.ui.editprofile.EditProfileActivity
import com.capstone.fotokui.ui.formphotographer.FormPhotographerActivity
import com.capstone.fotokui.ui.help.HelpActivity
import com.example.awesomedialog.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), EpoxyProfileController.OnProfileActivityListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var epoxyProfileController: EpoxyProfileController

    private val authViewModel: AuthViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private val awesomeDialog get() = AwesomeDialog.build(requireActivity())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getProfileScreenUiState()

        epoxyProfileController = EpoxyProfileController(requireContext(), this)

        binding.epoxyProfile.setController(epoxyProfileController)

        observeProfileScreenUiState()
    }

    override fun onClick(profileActivity: ProfileActivity) {
        when (profileActivity.title) {
            R.string.edit_service -> navigateToFormPhotographer()
            R.string.edit_profile -> navigateToEditProfileScreen()
            R.string.help -> navigateToHelpScreen()
            R.string.logout -> showDialogLogout()
        }
    }

    private fun observeProfileScreenUiState() {
        profileViewModel.profileScreenUiState.observe(viewLifecycleOwner) { profileScreenUiState ->
            epoxyProfileController.setData(profileScreenUiState)
        }
    }

    private fun showDialogLogout() {
        awesomeDialog
            .icon(R.drawable.ic_alert)
            .body(getString(R.string.logout_title))
            .onPositive(getString(R.string.ok), buttonBackgroundColor = R.drawable.filled_custom_button) {
                authViewModel.logout()
                awesomeDialog.cancel()
                navigateToAuthScreen()
            }
            .onNegative(
                getString(R.string.cancel),
                buttonBackgroundColor = R.drawable.outlined_custom_button,
                textColor = R.color.primary
            ) {
                awesomeDialog.cancel()
            }
    }

    private fun navigateToFormPhotographer() {
        val formPhotographerIntent = Intent(activity, FormPhotographerActivity::class.java).apply {
            putExtra(FormPhotographerActivity.EXTRA_FROM, FormPhotographerActivity.FROM_PROFILE)
        }
        startActivity(formPhotographerIntent)
    }

    private fun navigateToEditProfileScreen() {
        val authIntent = Intent(context, EditProfileActivity::class.java)
        startActivity(authIntent)
    }

    private fun navigateToHelpScreen() {
        val helpIntent = Intent(context, HelpActivity::class.java)
        startActivity(helpIntent)
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