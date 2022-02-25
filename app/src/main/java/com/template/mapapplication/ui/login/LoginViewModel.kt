package com.template.mapapplication.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.domain.login.Authentication
import com.template.domain.login.Registration

class LoginViewModel(
    private val sharedPrefsAuthImpl : Authentication,
    private val sharedPrefsRegImpl : Registration
    ) : ViewModel() {

    val text = MutableLiveData("This is Login Fragment")
}