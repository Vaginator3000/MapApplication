package com.template.mapapplication.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.template.data.login.SharedPrefsAuthenticationImpl
import com.template.data.login.SharedPrefsRegistrationImpl
import com.template.models.LoginUserModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.domain.login.Authentication
import com.template.domain.login.Registration

class LoginViewModel(
    private val sharedPrefsAuthImpl : Authentication,
    private val sharedPrefsRegImpl : Registration
    ) : ViewModel() {

    fun authenticate(loginOrEmail: String, password: String) : Boolean {
        return sharedPrefsAuthImpl.authenticate(loginOrEmail = loginOrEmail, password = password)
    }

    fun addUser(user: LoginUserModel) {
        sharedPrefsRegImpl.addUser(user = user)
    }

    fun checkUserAlreadyExist(login: String, email: String) : Boolean {
        return sharedPrefsRegImpl.isUserExist(login, email)
    }
