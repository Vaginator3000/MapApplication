package com.template.data.db

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.template.models.LoginUserModel

class SharedPrefsLoginDB(context: Context) {
    private val sPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    private val gson = Gson()

    suspend fun isUserExist(login: String, email: String) : Boolean {
        getAllUsers().forEach { user ->
            if (user.email == email || user.login == login)
                return true
        }
        return false
    }

    suspend fun authByData(loginOrEmail: String, password: String) : Boolean {
        getAllUsers().forEach { user ->
            if ( user.password == password && (user.email == loginOrEmail || user.login == loginOrEmail) )
                return true
        }
        return false
    }

    suspend fun authBySession() : LoginUserModel? {
        val userInStr = sPrefs.getString("session", null) ?: return null

        val type = object : TypeToken<LoginUserModel>() {}.type
        val user: LoginUserModel = gson.fromJson(userInStr, type)

        return user
    }

    suspend fun saveSession(loginOrEmail: String) {
        val user = getUserByLoginOrEmail(loginOrEmail)
        sPrefs.edit()
            .putString("session", gson.toJson(user))
            .apply()
    }

    suspend fun removeSession() {
        sPrefs.edit()
            .remove("session")
            .apply()
    }

    private suspend fun getUserByLoginOrEmail(loginOrEmail: String) : LoginUserModel {
        return getAllUsers().first { it.email == loginOrEmail || it.login == loginOrEmail }
    }

    suspend fun addUser(user: LoginUserModel) {
        var users = getAllUsers()
        if (users.isEmpty())
            users = listOf(user)
        else {
            users as MutableList
            users.add(user)
        }
        saveUsers(users)
    }

    private suspend fun getAllUsers(): List<LoginUserModel> {
        val listInStr = sPrefs.getString("users", null) ?: return listOf()

        val type = object : TypeToken<List<LoginUserModel>>() {}.type
        val users: List<LoginUserModel> = gson.fromJson(listInStr, type)
        return  users
    }

    private suspend fun saveUsers(users: List<LoginUserModel>) {
        sPrefs.edit()
            .putString("users", gson.toJson(users))
            .apply()
    }
}