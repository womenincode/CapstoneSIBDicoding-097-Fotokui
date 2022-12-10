package com.capstone.fotokui.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.FragmentRegisterBinding
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.Role
import com.capstone.fotokui.ui.formphotographer.FormPhotographerActivity
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

        setupRoleAutoCompleteTextView()
        setupListener()
        setupObserver()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_register -> register()
            R.id.btn_login -> navigateToLoginScreen()
        }
    }

    private fun setupRoleAutoCompleteTextView() {
        val roles = Role.values().map { role -> role.type }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_base_auto_complete_textview, roles)
        binding.actvRole.setAdapter(adapter)
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
                        .body("Akun ${response.result.email} berhasil dibuat!")
                        .icon(R.drawable.ic_success)
                        .onPositive(getString(R.string.ok), buttonBackgroundColor = R.drawable.filled_custom_button) {
                            navigateToNextScreen()
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
        val roleText = binding.actvRole.text.toString()
        val role = Role.valueOf(roleText.uppercase())
        val password = binding.tietPassword.text.toString()
        authViewModel.register(name, email, role, password)
    }

    private fun validateForm(): Boolean {
        if (binding.tietName.text?.isEmpty() == true) {
            binding.tilName.error = getString(R.string.name_empty)
            return false
        }
        if (binding.actvRole.text?.isEmpty() == true) {
            binding.actvRole.error = getString(R.string.role_empty)
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

    private fun navigateToNextScreen() {
        val roleText = binding.actvRole.text.toString()
        when (Role.valueOf(roleText.uppercase())) {
            Role.PENGGUNA -> navigateToLoginScreen()
            Role.FOTOGRAFER -> navigateToFormPhotographer()
        }
    }

    private fun navigateToLoginScreen() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun navigateToFormPhotographer() {
        val formPhotographerIntent = Intent(activity, FormPhotographerActivity::class.java).apply {
            putExtra(FormPhotographerActivity.EXTRA_FROM, FormPhotographerActivity.FROM_REGISTER)
        }
        startActivity(formPhotographerIntent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}