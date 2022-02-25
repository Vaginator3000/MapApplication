package com.template.data.login

import android.content.Context
import com.template.domain.login.Authentication
import com.template.models.LoginUserModel

class SharedPrefsAuthenticationImpl(context: Context) : Authentication {
    private val sharedPrefsDB = SharedPrefsDB(context)

    override fun authByData(loginOrEmail: String, password: String) : Boolean {
        return sharedPrefsDB.authByData(
            loginOrEmail = loginOrEmail,
            password = password
        )
    }

    override fun authBySession() : LoginUserModel? {
        return sharedPrefsDB.authBySession()
    }

    override fun saveSession(loginOrEmail: String) {
        return sharedPrefsDB.saveSession(loginOrEmail)
    }

    override fun removeSession() {
        return sharedPrefsDB.removeSession()
    }
}