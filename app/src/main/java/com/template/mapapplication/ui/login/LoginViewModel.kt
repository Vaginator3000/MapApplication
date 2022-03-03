package com.template.mapapplication.ui.login

import com.template.models.LoginUserModel
import com.template.models.Result
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.template.domain.login.Authentication
import com.template.domain.login.Registration
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sharedPrefsAuthImpl : Authentication,
    private val sharedPrefsRegImpl : Registration
    ) : ViewModel() {

    fun tryAuthenticationByData(loginOrEmail: String, password: String) = liveData<Result<LoginUserModel>> {
        if (sharedPrefsAuthImpl.authByData(loginOrEmail = loginOrEmail, password = password)) {
            emit(Result.Success())
        } else {
            emit(Result.Error())
        }
    }

    fun tryAuthenticationBySession() = liveData<Result<LoginUserModel>> {
        val sessionUser = sharedPrefsAuthImpl.authBySession()
        if (sessionUser == null) {
            emit(Result.Error())
        } else {
            emit(Result.Success(sessionUser))
        }
    }

    fun tryRegistration(login: String, email: String, password: String) = liveData<Result<LoginUserModel>>  {
        if (sharedPrefsRegImpl.isUserExist(login, email)) {
            emit(Result.Error())
        } else {
            val user = LoginUserModel(
                login = login,
                password = password,
                email = email
            )
            addUser(user)
            emit(Result.Success(user))
        }
    }

    fun saveSession(loginOrEmail: String) {
        viewModelScope.launch {
            sharedPrefsAuthImpl.saveSession(loginOrEmail)
        }
    }

    fun removeSession() {
        viewModelScope.launch {
            sharedPrefsAuthImpl.removeSession()
        }
    }

    private fun addUser(user: LoginUserModel) {
        viewModelScope.launch {
            sharedPrefsRegImpl.addUser(user = user)
        }
    }
}