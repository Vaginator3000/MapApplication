package com.template.mapapplication.ui.login

import com.template.models.LoginUserModel
import androidx.lifecycle.ViewModel
import com.template.domain.login.Authentication
import com.template.domain.login.Registration

class LoginViewModel(
    private val sharedPrefsAuthImpl : Authentication,
    private val sharedPrefsRegImpl : Registration
    ) : ViewModel() {

    fun authByData(loginOrEmail: String, password: String): Boolean {
        return sharedPrefsAuthImpl.authByData(loginOrEmail = loginOrEmail, password = password)
    }

    fun addUser(user: LoginUserModel) {
        sharedPrefsRegImpl.addUser(user = user)
    }

    fun checkUserAlreadyExist(login: String, email: String): Boolean {
        return sharedPrefsRegImpl.isUserExist(login, email)
    }

    fun authBySession() : LoginUserModel? {
        return sharedPrefsAuthImpl.authBySession()
    }

    fun saveSession(loginOrEmail: String) {
        sharedPrefsAuthImpl.saveSession(loginOrEmail)
    }

    fun removeSession() {
        sharedPrefsAuthImpl.removeSession()
    }
}