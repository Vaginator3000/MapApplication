package com.template.domain.login

import com.template.models.LoginUserModel

interface Authentication {
    fun authByData(loginOrEmail: String, password: String) : Boolean
    fun authBySession() : LoginUserModel?
    fun saveSession(loginOrEmail: String)
    fun removeSession()
}