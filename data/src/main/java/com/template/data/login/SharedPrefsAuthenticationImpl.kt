package com.template.data.login

import com.template.domain.login.Authentication
import com.template.models.LoginUserModel

class SharedPrefsAuthenticationImpl(val sharedPrefsDB : SharedPrefsDB) : Authentication {

    override suspend fun authByData(loginOrEmail: String, password: String) : Boolean {
        return sharedPrefsDB.authByData(
            loginOrEmail = loginOrEmail,
            password = password
        )
    }

    override suspend fun authBySession() : LoginUserModel? {
        return sharedPrefsDB.authBySession()
    }

    override suspend fun saveSession(loginOrEmail: String) {
        return sharedPrefsDB.saveSession(loginOrEmail)
    }

    override suspend fun removeSession() {
        return sharedPrefsDB.removeSession()
    }
}