package com.template.mapapplication.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapapplication.R
import com.template.mapapplication.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.text.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun setRegistrationMode() {
        with(binding) {
            registrationBtn.alpha = 0.7f
            authBtn.alpha = 1f
            registrationGroup.isVisible = true
            authenticationGroup.isVisible = false
        }
    }

    private fun setAuthMode() {
        with(binding) {
            authBtn.alpha = 0.7f
            registrationBtn.alpha = 1f
            authenticationGroup.isVisible = true
            registrationGroup.isVisible = false
        }
    }

}
