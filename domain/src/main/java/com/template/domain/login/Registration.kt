package com.template.domain.login

import com.template.models.LoginUserModel

interface Registration {
    suspend fun addUser(user: LoginUserModel)
    suspend fun isUserExist(login: String, email: String) : Boolean
}