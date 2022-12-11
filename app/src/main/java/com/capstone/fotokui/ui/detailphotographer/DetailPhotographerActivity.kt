package com.capstone.fotokui.ui.detailphotographer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.ActivityDetailPhotographerBinding
import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.domain.Response
import com.example.awesomedialog.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPhotographerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPhotographerBinding

    private val detailPhotographerViewModel: DetailPhotographerViewModel by viewModels()

    private val awesomeDialog get() = AwesomeDialog.build(this)

    private var photographerId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPhotographerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photographerId = intent?.getStringExtra(EXTRA_PHOTOGRAPHER_ID)

        detailPhotographerViewModel.getPhotographer(photographerId.toString())

        setupListener()
        observeDetailPhotographer()
    }

    private fun setupListener() {
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun setupButtonWhatsapp(phone: String?) {
        val whatsapp = "https://api.whatsapp.com/send?phone=$phone"
        val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(whatsapp)
        }
        startActivity(whatsappIntent)
    }

    private fun observeDetailPhotographer() {
        detailPhotographerViewModel.currentPhotographer.observe(this) { response ->
            when (response) {
                is Response.Loading -> {
                    binding.btnMessagePhotographer.isEnabled = false
                }
                is Response.Success -> {
                    binding.btnMessagePhotographer.isEnabled = true
                    setupDetailPhotographer(response.result)
                }
                is Response.Failure -> {
                    binding.btnMessagePhotographer.isEnabled = true
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

    private fun setupDetailPhotographer(photographer: Photographer) {
        binding.photographer = photographer
        binding.btnMessagePhotographer.setOnClickListener {
            setupButtonWhatsapp(photographer.phone)
        }
    }

    companion object {
        const val EXTRA_PHOTOGRAPHER_ID = "extra_photographer_id"
    }
}