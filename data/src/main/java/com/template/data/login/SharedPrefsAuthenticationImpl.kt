package com.template.data.login

import android.content.Context
import com.template.domain.login.Authentication

class SharedPrefsAuthenticationImpl(context: Context) : Authentication {
    private val sharedPrefsDB = SharedPrefsDB(context)

    override fun authenticate(loginOrEmail: String, password: String) : Boolean {
        return sharedPrefsDB.authenticateByData(
            loginOrEmail = loginOrEmail,
            password = password
        )
    }
}