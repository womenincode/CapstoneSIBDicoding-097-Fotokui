package com.capstone.fotokui.ui.formphotographer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.fotokui.MainActivity
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.ActivityFormPhotographerBinding
import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.ui.auth.AuthViewModel
import com.example.awesomedialog.*
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormPhotographerActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFormPhotographerBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val formPhotographerViewModel: FormPhotographerViewModel by viewModels()

    private var currentUser: FirebaseUser? = null

    private val fromType: Int get() {
        return if (intent.getIntExtra(EXTRA_FROM, FROM_REGISTER) == FROM_REGISTER)
            FROM_REGISTER else FROM_PROFILE
    }

    private val awesomeDialog get() = AwesomeDialog.build(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPhotographerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = authViewModel.currentUser

        if (fromType == FROM_PROFILE) {
            formPhotographerViewModel.getPhotographer(currentUser?.uid.toString())
            observeCurrentPhotographer()
        }

        setupRoleAutoCompleteTextView()
        setupListener()
        observeFormPhotographerState()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_cancel -> {
                if (fromType == FROM_REGISTER) {
                    navigateToHomeScreen()
                } else {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
            R.id.btn_save -> updateFormPhotographer()
        }
    }

    private fun setupRoleAutoCompleteTextView() {
        val monthOrYear = listOf(getString(R.string.year), getString(R.string.month))
        val adapter = ArrayAdapter(this, R.layout.item_base_auto_complete_textview, monthOrYear)
        binding.actvMonthOrYear.setAdapter(adapter)
    }

    private fun setupListener() {
        binding.toolbar.setNavigationOnClickListener {
            if (fromType == FROM_REGISTER) {
                navigateToHomeScreen()
            } else {
                onBackPressedDispatcher.onBackPressed()
            }
        }
        arrayOf(binding.btnCancel, binding.btnSave)
            .forEach { button -> button.setOnClickListener(this) }
    }

    private fun setupFormPhotographer(photographer: Photographer) {
        photographer.experience?.let { binding.tietExperience.setText(it.toString()) }
        photographer.yearOrMonthExperience?.let { binding.actvMonthOrYear.setText(it) }
        photographer.price?.let { binding.tietPrice.setText(it.toString()) }
        photographer.promo?.let { binding.tietPromotion.setText(it.toString()) }
        photographer.phone?.let { binding.tietPhone.setText(it) }
        photographer.description?.let { binding.tietDescription.setText(it) }
        photographer.location?.let { binding.tietLocation.setText(it) }
    }

    private fun observeFormPhotographerState() {
        formPhotographerViewModel.updatePhotographerState.observe(this) { response ->
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

    private fun observeCurrentPhotographer() {
        formPhotographerViewModel.currentPhotographer.observe(this) { response ->
            when (response) {
                is Response.Loading -> {
                    binding.btnCancel.isEnabled = false
                    binding.btnSave.isEnabled = false
                }
                is Response.Success -> {
                    binding.btnCancel.isEnabled = true
                    binding.btnSave.isEnabled = true
                    setupFormPhotographer(response.result)
                    setupRoleAutoCompleteTextView()
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

    private fun updateFormPhotographer() {

        if (!validateForm()) return

        val photographerUid = currentUser?.uid.toString()
        val photographerPhotoProfile = currentUser?.photoUrl?.toString()!!
        val photographerName = currentUser?.displayName.toString()
        val photographerEmail = currentUser?.email.toString()
        val photographerExperience = binding.tietExperience.text.toString().toFloat()
        val yearOrMonthExperience = binding.actvMonthOrYear.text.toString()
        val photographerPrice = binding.tietPrice.text.toString().toInt()
        val photographerPromotion = binding.tietPromotion.text.toString().toFloat()
        val photographerPhone = binding.tietPhone.text.toString()
        val photographerDescription = binding.tietDescription.text.toString()
        val photographerLocation = binding.tietLocation.text.toString()
        val photographerPhotos = emptyList<String>()

        formPhotographerViewModel.updatePhotographer(
            id = photographerUid,
            photo = photographerPhotoProfile,
            name = photographerName,
            email = photographerEmail,
            experience = photographerExperience,
            yearOrMonthExperience = yearOrMonthExperience,
            price = photographerPrice,
            promo = photographerPromotion,
            phone = photographerPhone,
            description = photographerDescription,
            location = photographerLocation,
            photos = photographerPhotos
        )
    }

    private fun validateForm(): Boolean {
        if (binding.tietExperience.text?.isEmpty() == true) {
            binding.tilExperience.error = getString(R.string.experience_empty)
            return false
        }
        if (binding.actvMonthOrYear.text?.isEmpty() == true) {
            binding.tilYearOrMonth.error = getString(R.string.year_or_month_empty)
            return false
        }
        if (binding.tietPrice.text?.isEmpty() == true) {
            binding.tilPrice.error = getString(R.string.price_empty)
            return false
        }
        if (binding.tietPromotion.text?.isEmpty() == true) {
            binding.tilPromotion.error = getString(R.string.promotion_empty)
            return false
        }
        if (binding.tietPhone.text?.isEmpty() == true) {
            binding.tilPhone.error = getString(R.string.no_hp_empty)
            return false
        }
        if (binding.tietDescription.text?.isEmpty() == true) {
            binding.tilDescription.error = getString(R.string.description_empty)
            return false
        }
        if (binding.tietLocation.text?.isEmpty() == true) {
            binding.tilLocation.error = getString(R.string.location_empty)
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

    companion object {
        const val EXTRA_FROM = "extra_type"
        const val FROM_REGISTER = 0
        const val FROM_PROFILE = 1
    }

}