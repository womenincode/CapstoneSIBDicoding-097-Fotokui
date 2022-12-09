package com.capstone.fotokui.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.fotokui.databinding.ActivityRegisterBinding
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            authViewModel.register(
                name = binding.tietName.text.toString().trim(),
                email = binding.tietEmail.text.toString().trim(),
                password = binding.tietPassword.text.toString().trim()
            )
        }


        authViewModel.registerLiveData.observe(this){
            when(it){
                is Response.Success -> {
                    binding.progressBar.visibility = View.GONE
                    showToast("Pendaftaran berhasil !")
                    startActivity(Intent(this,LoginActivity::class.java))
                }
                is Response.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    showToast(it.message)
                }
                is Response.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
    }
}