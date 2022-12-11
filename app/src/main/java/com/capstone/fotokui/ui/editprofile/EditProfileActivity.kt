package com.capstone.fotokui.ui.editprofile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.capstone.fotokui.MainActivity
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.ActivityEditProfileBinding
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.Role
import com.capstone.fotokui.domain.User
import com.capstone.fotokui.ui.auth.AuthViewModel
import com.capstone.fotokui.utils.reduceFileImage
import com.capstone.fotokui.utils.uriToFile
import com.example.awesomedialog.*
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEditProfileBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val editProfileViewModel: EditProfileViewModel by viewModels()

    private var currentUser: FirebaseUser? = null
    private var profilePhoto: File? = null

    private var isPhotoProfileNeedUpdate = false

    private var userRole = Role.PENGGUNA

    private val awesomeDialog get() = AwesomeDialog.build(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = authViewModel.currentUser

        editProfileViewModel.getCurrentUser()

        setupListener()
        observeCurrentUser()
        observeEditProfileState()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.riv_profile -> startGallery()
            R.id.btn_cancel -> {
                showDialogEditProfile(
                    R.string.cancel_edit_profile_title,
                    R.string.cancel_edit_profile_body
                ) { onBackPressedDispatcher.onBackPressed() }
            }
            R.id.btn_save -> {
                if (!validateForm()) return
                showDialogEditProfile(
                    R.string.save_edit_profile_title,
                    R.string.save_edit_profile_body
                ) { updateProfile() }
            }
        }
    }

    private fun setupListener() {
        binding.toolbar.setNavigationOnClickListener {
            showDialogEditProfile(
                R.string.cancel_edit_profile_title,
                R.string.cancel_edit_profile_body
            ) { onBackPressedDispatcher.onBackPressed() }
        }
        arrayOf(binding.rivProfile, binding.btnCancel, binding.btnSave)
            .forEach { button -> button.setOnClickListener(this) }
    }

    private fun setupFormEditProfile(user: User) {
        user.photo?.let {
            binding.rivProfile.load(it) {
                placeholder(R.drawable.ic_profile_placeholder)
                error(R.drawable.ic_profile_error)
            }
        }
        user.name?.let { binding.tietName.setText(it) }
        user.email?.let { binding.tietEmail.setText(it) }
        user.phone?.let { binding.tietPhone.setText(it) }
        user.location?.let { binding.tietLocation.setText(it) }
    }

    private fun observeCurrentUser() {
        editProfileViewModel.currentUser.observe(this) { response ->
            when (response) {
                is Response.Loading -> {
                    binding.btnCancel.isEnabled = false
                    binding.btnSave.isEnabled = false
                }
                is Response.Success -> {
                    binding.btnCancel.isEnabled = true
                    binding.btnSave.isEnabled = true
                    response.result.role?.let { userRole = Role.valueOf(it) }
                    isPhotoProfileNeedUpdate = response.result.photo == null
                    setupFormEditProfile(response.result)
                }
                is Response.Failure -> {
                    binding.btnCancel.isEnabled = true
                    binding.btnSave.isEnabled = true
                    awesomeDialog.title("Gagal mendapatkan data")
                        .body(response.message)
                        .icon(R.drawable.ic_error)
                        .onPositive(getString(R.string.ok), buttonBackgroundColor = R.drawable.filled_custom_button) {
                            awesomeDialog.cancel()
                        }
                        .show()
                }
            }
        }
    }

    private fun observeEditProfileState() {
        editProfileViewModel.updateUserState.observe(this) { response ->
            when (response) {
                is Response.Loading -> {
                    binding.btnCancel.isEnabled = false
                    binding.btnSave.isEnabled = false
                }
                is Response.Success -> {
                    binding.btnCancel.isEnabled = true
                    binding.btnSave.isEnabled = true
                    awesomeDialog.title("Update berhasil")
                        .body(response.result)
                        .icon(R.drawable.ic_success)
                        .onPositive(getString(R.string.ok), buttonBackgroundColor = R.drawable.filled_custom_button) {
                            navigateToHomeScreen()
                        }
                        .show()
                }
                is Response.Failure -> {
                    binding.btnCancel.isEnabled = true
                    binding.btnSave.isEnabled = true
                    awesomeDialog.title("Update gagal")
                        .body(response.message)
                        .icon(R.drawable.ic_error)
                        .onPositive(getString(R.string.ok), buttonBackgroundColor = R.drawable.filled_custom_button) {
                            awesomeDialog.cancel()
                        }
                        .show()
                }
            }
        }
    }

    private fun updateProfile() {

        val photographerUid = currentUser?.uid.toString()
        val photographerName = binding.tietName.text.toString()
        val photographerPhone = binding.tietPhone.text.toString()
        val photographerLocation = binding.tietLocation.text.toString()

        val compressedPhoto = profilePhoto?.let { reduceFileImage(it) }

        editProfileViewModel.updateUser(
            id = photographerUid,
            photo = compressedPhoto,
            name = photographerName,
            phone = photographerPhone,
            location = photographerLocation,
            role = userRole
        )
    }

    private fun showDialogEditProfile(
        @StringRes title: Int,
        @StringRes body: Int,
        onPositiveClicked: () -> Unit
    ) {
        awesomeDialog
            .icon(R.drawable.ic_alert)
            .title(getString(title))
            .body(getString(body))
            .onPositive(getString(R.string.ok), buttonBackgroundColor = R.drawable.filled_custom_button) {
                awesomeDialog.cancel()
                onPositiveClicked()
            }
            .onNegative(
                getString(R.string.cancel),
                buttonBackgroundColor = R.drawable.outlined_custom_button,
                textColor = R.color.primary
            ) {
                awesomeDialog.cancel()
            }
    }

    private fun validateForm(): Boolean {
        if (binding.tietName.text?.isEmpty() == true) {
            binding.tietName.error = getString(R.string.name_empty)
            return false
        }
        if (binding.tietEmail.text?.isEmpty() == true) {
            binding.tietEmail.error = getString(R.string.email_empty)
            return false
        }
        if (binding.tietPhone.text?.isEmpty() == true) {
            binding.tietPhone.error = getString(R.string.phone_empty)
            return false
        }
        if (binding.tietLocation.text?.isEmpty() == true) {
            binding.tilLocation.error = getString(R.string.no_hp_empty)
            return false
        }
        return true
    }

    private fun navigateToHomeScreen() {
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(homeIntent)
        finish()
    }

    private fun startGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri = result.data?.data as Uri
            val imageFile = uriToFile(selectedImage, this)
            profilePhoto = imageFile
            binding.rivProfile.setImageURI(selectedImage)
        }
    }
}