package com.template.mapapplication.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.template.data.login.SharedPrefsAuthenticationImpl
import com.template.data.login.SharedPrefsRegistrationImpl
import com.template.models.LoginUserModel

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    private val sharedPrefsAuthImpl by lazy { SharedPrefsAuthenticationImpl(context = getApplication<Application>().applicationContext) }
    private val sharedPrefsRegImpl by lazy { SharedPrefsRegistrationImpl(context = getApplication<Application>().applicationContext) }

    fun authenticate(loginOrEmail: String, password: String) : Boolean {
        return sharedPrefsAuthImpl.authenticate(loginOrEmail = loginOrEmail, password = password)
    }

    fun addUser(user: LoginUserModel) {
        sharedPrefsRegImpl.addUser(user = user)
    }

    fun checkUserAlreadyExist(login: String, email: String) : Boolean {
        return sharedPrefsRegImpl.isUserExist(login, email)
    }
}