package com.capstone.fotokui.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.FragmentProfileBinding
import com.capstone.fotokui.domain.ProfileActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var epoxyProfileController: EpoxyProfileController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        epoxyProfileController = EpoxyProfileController(requireContext())

        binding.epoxyProfile.setController(epoxyProfileController)

        epoxyProfileController.setData(profileActivities())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}