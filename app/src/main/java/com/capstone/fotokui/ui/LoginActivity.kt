package com.capstone.fotokui.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.capstone.fotokui.MainActivity
import com.capstone.fotokui.databinding.ActivityLoginBinding
import com.capstone.fotokui.utils.ResponseResult
import com.capstone.fotokui.view_model.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            authViewModel.login(
                email = binding.edtEmail.text.toString().trim(),
                password = binding.edtPassword.text.toString().trim()
            )
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        authViewModel.loginLiveData.observe(this){
            when(it){
                is ResponseResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
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

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}