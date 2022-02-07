package com.template.data.login

import android.content.Context
import com.template.domain.login.Registration
import com.template.models.LoginUserModel

class SharedPrefsRegistrationImpl(context: Context) : Registration {

    private val sharedPrefsDB = SharedPrefsDB(context)

    override fun addUser(user: LoginUserModel) {
        sharedPrefsDB.addUser(user)
    }

    override fun isUserExist(login: String, email: String) : Boolean {
        return sharedPrefsDB.isUserExist(login, email)
    }
}