package com.capstone.fotokui.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.FragmentRegisterBinding
import com.capstone.fotokui.domain.Response
import com.example.awesomedialog.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    private val awesomeDialog get() = AwesomeDialog.build(requireActivity())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupObserver()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_register -> register()
            R.id.btn_login -> navigateToLoginScreen()
        }
    }

    private fun setupListener() {
        arrayOf(binding.btnLogin, binding.btnRegister)
            .forEach { button -> button.setOnClickListener(this) }
    }

    private fun setupObserver() {
        authViewModel.registerLiveData.observe(viewLifecycleOwner) { response ->
            when(response){
                is Response.Success -> {
                    binding.btnLogin.isEnabled = true
                    awesomeDialog.title("Registrasi berhasil")
                        .body("Akun ${response.result.displayName} berhasil dibuat!")
                        .icon(R.drawable.ic_success)
                        .onPositive(getString(R.string.ok)) {
                            awesomeDialog.cancel()
                            navigateToLoginScreen()
                        }
                        .show()
                }
                is Response.Failure -> {
                    binding.btnRegister.isEnabled = true
                    awesomeDialog.title("Register gagal")
                        .body(response.message)
                        .icon(R.drawable.ic_error)
                        .onPositive(getString(R.string.ok), buttonBackgroundColor = R.drawable.filled_custom_button) {
                            awesomeDialog.cancel()
                        }
                        .show()
                }
                is Response.Loading -> {
                    binding.btnRegister.isEnabled = false
                }
                else -> {}
            }
        }
    }

    private fun register() {
        if (!validateForm()) return
        val name = binding.tietName.text.toString()
        val email = binding.tietEmail.text.toString()
        val password = binding.tietPassword.text.toString()
        authViewModel.register(name, email, password)
    }

    private fun validateForm(): Boolean {
        if (binding.tietName.text?.isEmpty() == true) {
            binding.tilName.error = getString(R.string.name_empty)
            return false
        }
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

    private fun navigateToLoginScreen() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}