package com.template.domain.login

interface Authentication {
    fun authenticate(loginOrEmail: String, password: String) : Boolean
}