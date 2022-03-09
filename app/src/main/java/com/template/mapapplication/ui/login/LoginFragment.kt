package com.template.mapapplication.ui.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.template.models.Result
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapapplication.R
import com.template.mapapplication.databinding.FragmentLoginBinding
import com.template.models.LoginUserModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAuthMode()
        setOnClicks()
        tryAuthBySession()
    }

    private fun tryAuthBySession() {
        loginViewModel.tryAuthenticationBySession().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Error -> {
                    // do nothing
                }
                is Result.Success<LoginUserModel> -> {
                    showAuthBySessionDialog(login = result.value!!.login)
                }
            }
        })
    }

    private fun setOnClicks() {
        with(binding) {
            registrationBtn.setOnClickListener {
                setRegistrationMode()
            }

            authBtn.setOnClickListener {
                setAuthMode()
            }

            continueAuthBtn.setOnClickListener {
                if (checkFieldsAreCorrectAndNotEmpty()) {
                    val loginOrEmail = loginOrEmailEditText.text.toString().trim()
                    val password = passwordEditText.text.toString()

                    loginViewModel.tryAuthenticationByData(loginOrEmail = loginOrEmail, password = password)
                        .observe(viewLifecycleOwner, Observer { result ->
                            when (result) {
                                is Result.Error -> {
                                    showToast(getString(R.string.incorrect_login_or_password))
                                }
                                is Result.Success<*> -> {
                                    showSaveSessionDialog(loginOrEmail = loginOrEmail)
                                    navigateToNextFragment()
                                }
                            }
                        })
                }
            }

            continueRegBtn.setOnClickListener {
                if (checkFieldsAreCorrectAndNotEmpty()) {
                    val login = loginEditText.text.toString().trim()
                    val email = emailEditText.text.toString().trim()
                    val pass1 = pass1EditText.text.toString()

                    loginViewModel.tryRegistration(login = login, email = email, password = pass1)
                        .observe(viewLifecycleOwner, Observer { result ->
                            when (result) {
                                is Result.Error -> {
                                    showToast(getString(R.string.user_already_exists))
                                }
                                is Result.Loading -> {
                                    // do nothing
                                }
                                is Result.Success -> {
                                    showSaveSessionDialog(loginOrEmail = login)
                                    navigateToNextFragment()
                                }
                            }
                        })
                }
            }
        }
    }

    private fun navigateToNextFragment() {
        val navController = findNavController()
        navController.navigate(R.id.action_login_fragment_to_navigation_map)
    }

    private fun checkFieldsAreCorrectAndNotEmpty() : Boolean {
        with(binding) {
            if (registrationGroup.isVisible) {
                if (loginEditText.text.toString().isEmpty() || emailEditText.text.toString().isEmpty() ||
                    pass1EditText.text.toString().isEmpty() || pass2EditText.text.toString().isEmpty()  ) {
                    showToast(message = getString(R.string.fields_cant_be_empty))
                    return false
                }
                if (!acceptSwitch.isChecked) {
                    showToast(message = getString(R.string.check_privacy_policy))
                    return false
                }
                if (pass1EditText.text.toString() != pass2EditText.text.toString()) {
                    showToast(message = getString(R.string.passwords_are_different))
                    return false
                }
            }

            if (authenticationGroup.isVisible) {
                if (loginOrEmailEditText.text.toString().isEmpty() || passwordEditText.text.toString().isEmpty()) {
                    showToast(message = getString(R.string.fields_cant_be_empty))
                    return false
                }
            }
        }
        return true
    }

    private fun showAuthBySessionDialog(login: String) {
        val loginAs = String.format(getString(R.string.login_as_request), login)
        AlertDialog.Builder(requireContext())
            .setMessage(loginAs)
            .setPositiveButton(getString(R.string.positive_answer)) { _, _ ->
                navigateToNextFragment()
            }
            .setNegativeButton(getString(R.string.negative_answer)) { _, _ ->
                loginViewModel.removeSession()
            }
            .create()
            .show()
    }

    private fun showSaveSessionDialog(loginOrEmail: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.request_to_save_session))
            .setPositiveButton(getString(R.string.positive_answer)) { _, _ ->
                loginViewModel.saveSession(loginOrEmail = loginOrEmail)
            }
            .setNegativeButton(getString(R.string.negative_answer)) { _, _ -> }
            .create()
            .show()

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
