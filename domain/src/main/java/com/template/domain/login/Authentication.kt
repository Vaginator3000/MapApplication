package com.template.domain.login

import com.template.models.LoginUserModel

interface Authentication {
    suspend fun authByData(loginOrEmail: String, password: String) : Boolean
    suspend fun authBySession() : LoginUserModel?
    suspend fun saveSession(loginOrEmail: String)
    suspend fun removeSession()
}