package com.template.mapapplication.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapapplication.R
import com.template.mapapplication.databinding.FragmentLoginBinding
import com.template.models.LoginUserModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val loginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAuthMode()
        setOnClicks()
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
                val loginOrEmail = (loginOrEmailEditText.text ?: return@setOnClickListener).toString().trim()
                val password = (passwordEditText.text ?: return@setOnClickListener).toString()

                if (!checkFieldsAreCorrectAndNotEmpty()) return@setOnClickListener

                if (loginViewModel.authenticate(loginOrEmail = loginOrEmail, password = password)) {
                    // navigateToNextFragment()
                } else
                    showToast(getString(R.string.incorrect_login_or_password))
            }

            continueRegBtn.setOnClickListener {
                val login = (loginEditText.text ?: return@setOnClickListener).toString().trim()
                val email = (emailEditText.text ?: return@setOnClickListener).toString().trim()
                val pass1 = (pass1EditText.text ?: return@setOnClickListener).toString()

                if (!checkFieldsAreCorrectAndNotEmpty()) return@setOnClickListener

                if (loginViewModel.checkUserAlreadyExist(login = login, email = email)) {
                    showToast(message = getString(R.string.user_already_exists))
                    return@setOnClickListener
                }

                loginViewModel.addUser(
                    user = LoginUserModel(
                        login = login,
                        password = pass1,
                        email = email
                    )
                )
                // navigateToNextFragment()
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
