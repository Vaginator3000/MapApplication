package com.template.data.login

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.template.models.LoginUserModel

class SharedPrefsDB(context: Context) {
    private val sPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun authenticateByData(loginOrEmail: String, password: String) : Boolean {
        getAllUsers().forEach { user ->
            if ( user.password == password && (user.email == loginOrEmail || user.login == loginOrEmail) )
                return true
        }
        return false
    }

    private fun getAllUsers(): List<LoginUserModel> {
        val listInStr = sPrefs.getString("users", null) ?: return listOf()

        val type = object : TypeToken<List<LoginUserModel>>() {}.type
        val users: List<LoginUserModel> = gson.fromJson(listInStr, type)
        return  users
    }
}