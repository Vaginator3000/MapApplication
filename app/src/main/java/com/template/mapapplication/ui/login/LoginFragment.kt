package com.template.mapapplication.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapapplication.R
import com.template.mapapplication.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val loginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.text.observe(viewLifecycleOwner, Observer {
        
        })
    }

    private fun setRegistrationMode() {
        with(binding) {
            registrationBtn.alpha = 0.7f
            authBtn.alpha = 1f
            registrationGroup.visibility = View.VISIBLE
            authenticationGroup.visibility = View.GONE
        }
    }

    private fun setAuthMode() {
        with(binding) {
            authBtn.alpha = 0.7f
            registrationBtn.alpha = 1f
            authenticationGroup.visibility = View.VISIBLE
            registrationGroup.visibility = View.GONE
        }
    }
}
