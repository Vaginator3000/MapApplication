package com.template.mapapplication.ui.login

import com.template.models.LoginUserModel
import androidx.lifecycle.ViewModel
import com.template.domain.login.Authentication
import com.template.domain.login.Registration

class LoginViewModel(
    private val sharedPrefsAuthImpl : Authentication,
    private val sharedPrefsRegImpl : Registration
    ) : ViewModel() {

    suspend fun authByData(loginOrEmail: String, password: String): Boolean {
        return sharedPrefsAuthImpl.authByData(loginOrEmail = loginOrEmail, password = password)
    }

    suspend fun addUser(user: LoginUserModel) {
        sharedPrefsRegImpl.addUser(user = user)
    }

    suspend fun checkUserAlreadyExist(login: String, email: String): Boolean {
        return sharedPrefsRegImpl.isUserExist(login, email)
    }

    suspend fun authBySession() : LoginUserModel? {
        return sharedPrefsAuthImpl.authBySession()
    }

    suspend fun saveSession(loginOrEmail: String) {
        sharedPrefsAuthImpl.saveSession(loginOrEmail)
    }

    suspend fun removeSession() {
        sharedPrefsAuthImpl.removeSession()
    }
}