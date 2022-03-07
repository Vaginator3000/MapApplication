package com.template.data.login

import com.template.data.db.SharedPrefsLoginDB
import com.template.domain.login.Registration
import com.template.models.LoginUserModel

class SharedPrefsRegistrationImpl(val sharedPrefsDB : SharedPrefsLoginDB) : Registration {

    override suspend fun addUser(user: LoginUserModel) {
        sharedPrefsDB.addUser(user)
    }

    override suspend fun isUserExist(login: String, email: String) : Boolean {
        return sharedPrefsDB.isUserExist(login, email)
    }
}