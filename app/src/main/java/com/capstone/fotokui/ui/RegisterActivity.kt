package com.capstone.fotokui.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.ActivityRegisterBinding
import com.capstone.fotokui.utils.ResponseResult
import com.capstone.fotokui.view_model.AuthViewModel
import com.google.android.gms.common.api.Response
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
                name = binding.edtName.text.toString().trim(),
                email = binding.edtEmail.text.toString().trim(),
                password = binding.edtPassword.text.toString().trim()
            )
        }


        authViewModel.registerLiveData.observe(this){
            when(it){
                is ResponseResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    showToast("Pendaftaran berhasil !")
                    startActivity(Intent(this,LoginActivity::class.java))
                }
                is ResponseResult.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    showToast(it.exception.message.toString())
                }
                is ResponseResult.Loading -> {
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