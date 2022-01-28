package com.template.data.login

import android.content.Context
import com.google.gson.Gson

class SharedPrefsDB(context: Context) {
    private val sPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    private val gson = Gson()

    //логика авторизации и регистрации через префы будет тут
}