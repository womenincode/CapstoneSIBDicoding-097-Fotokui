package com.capstone.fotokui.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.fotokui.MainActivity
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.FragmentLoginBinding
import com.capstone.fotokui.domain.Response
import com.example.awesomedialog.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    private val awesomeDialog get() = AwesomeDialog.build(requireActivity())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupObserver()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login -> login()
            R.id.btn_register -> navigateToRegisterScreen()
        }
    }

    private fun setupListener() {
        arrayOf(binding.btnLogin, binding.btnRegister)
            .forEach { button -> button.setOnClickListener(this) }
    }

    private fun setupObserver() {
        authViewModel.loginLiveData.observe(viewLifecycleOwner) { response ->
            when(response){
                is Response.Success -> {
                    binding.btnLogin.isEnabled = true
                    navigateToHomeScreen()
                }
                is Response.Failure -> {
                    binding.btnLogin.isEnabled = true
                    awesomeDialog.title("Login gagal")
                        .body(response.message)
                        .icon(R.drawable.ic_error)
                        .onPositive(getString(R.string.ok), buttonBackgroundColor = R.drawable.filled_custom_button) {
                            awesomeDialog.cancel()
                        }
                        .show()
                }
                is Response.Loading -> {
                    binding.btnLogin.isEnabled = false
                }
                else -> {}
            }
        }
    }

    private fun login() {
        if (!validateForm()) return
        val email = binding.tietEmail.text.toString()
        val password = binding.tietPassword.text.toString()
        authViewModel.login(email, password)
    }

    private fun validateForm(): Boolean {
        if (binding.tietEmail.text?.isEmpty() == true) {
            binding.tilEmail.error = getString(R.string.email_empty)
            return false
        }
        if (binding.tietPassword.text?.isEmpty() == true) {
            binding.tilPassword.error = getString(R.string.password_empty)
            return false
        }
        return true
    }

    private fun navigateToRegisterScreen() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    private fun navigateToHomeScreen() {
        val homeIntent = Intent(requireContext(), MainActivity::class.java)
        startActivity(homeIntent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}