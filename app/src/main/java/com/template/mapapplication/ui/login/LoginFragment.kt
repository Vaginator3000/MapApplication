package com.template.mapapplication.ui.login

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapapplication.R
import com.template.mapapplication.databinding.FragmentLoginBinding
import com.template.models.LoginUserModel
import kotlinx.coroutines.*
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

    private fun startInBackground(foo: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            foo()
        }
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

                    startInBackground {
                        if (loginViewModel.authByData(loginOrEmail = loginOrEmail, password = password)) {
                            showSaveSessionDialog(loginOrEmail = loginOrEmail)
                            // navigateToNextFragment()
                        } else {
                            showToast(getString(R.string.incorrect_login_or_password))
                        }
                    }
                }
            }

            continueRegBtn.setOnClickListener {
                if (checkFieldsAreCorrectAndNotEmpty()) {
                    val login = loginEditText.text.toString().trim()
                    val email = emailEditText.text.toString().trim()
                    val pass1 = pass1EditText.text.toString()

                    startInBackground {
                        if (loginViewModel.checkUserAlreadyExist(login = login, email = email)) {
                            showToast(message = getString(R.string.user_already_exists))
                        } else {
                            loginViewModel.addUser(
                                user = LoginUserModel(
                                    login = login,
                                    password = pass1,
                                    email = email
                                )
                            )
                            showSaveSessionDialog(loginOrEmail = login)
                            // navigateToNextFragment()
                        }
                    }
                }
            }
        }
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

    private fun tryAuthBySession() {
        loginViewModel //init before background
        startInBackground {
            val user = loginViewModel.authBySession()
            if (user != null) {
                showAuthBySessionDialog(login = user.login)
            }
        }
    }

    private suspend fun showAuthBySessionDialog(login: String) {
        withContext(Dispatchers.Main) {
            val loginAs = String.format(getString(R.string.login_as_request), login)
            AlertDialog.Builder(requireContext())
                .setMessage(loginAs)
                .setPositiveButton(getString(R.string.positive_answer)) { _, _ ->
                    startInBackground {
                        loginViewModel.authBySession()
                        // navigateToNextFragment()
                    }
                }
                .setNegativeButton(getString(R.string.negative_answer)) { _, _ ->
                    startInBackground {
                        loginViewModel.removeSession()
                    }
                }
                .create()
                .show()
        }
    }

    private suspend fun showSaveSessionDialog(loginOrEmail: String) {
        withContext(Dispatchers.Main) {
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.request_to_save_session))
                .setPositiveButton(getString(R.string.positive_answer)) { _, _ ->
                    startInBackground {
                        loginViewModel.saveSession(loginOrEmail = loginOrEmail)
                    }
                }
                .setNegativeButton(getString(R.string.negative_answer)) { _, _ -> }
                .create()
                .show()
        }
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
