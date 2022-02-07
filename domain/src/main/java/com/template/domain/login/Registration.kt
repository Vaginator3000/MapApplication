package com.template.domain.login

import com.template.models.LoginUserModel

interface Registration {
    fun addUser(user: LoginUserModel)
    fun isUserExist(login: String, email: String) : Boolean
}