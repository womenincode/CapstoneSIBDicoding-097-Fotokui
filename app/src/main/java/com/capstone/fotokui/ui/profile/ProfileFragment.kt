package com.capstone.fotokui.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.FragmentProfileBinding
import com.capstone.fotokui.domain.ProfileActivity
import com.capstone.fotokui.ui.auth.AuthActivity
import com.capstone.fotokui.ui.auth.AuthViewModel
import com.example.awesomedialog.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), EpoxyProfileController.OnProfileActivityListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var epoxyProfileController: EpoxyProfileController

    private val authViewModel: AuthViewModel by viewModels()

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

        epoxyProfileController = EpoxyProfileController(requireContext(), this)

        binding.epoxyProfile.setController(epoxyProfileController)

        epoxyProfileController.setData(profileActivities())
    }

    override fun onClick(profileActivity: ProfileActivity) {
        when (profileActivity.title) {
            getString(R.string.logout) -> showDialogLogout()
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

    private fun profileActivities(): List<ProfileActivity> {
        val profileActivities = ArrayList<ProfileActivity>()
        profileActivities.add(ProfileActivity(
            ResourcesCompat.getDrawable(resources, R.drawable.ic_help, null),
            getString(R.string.help)
        ))
        profileActivities.add(ProfileActivity(
            ResourcesCompat.getDrawable(resources, R.drawable.ic_logout, null),
            getString(R.string.logout)
        ))
        return profileActivities
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