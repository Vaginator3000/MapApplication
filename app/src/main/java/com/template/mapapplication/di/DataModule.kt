package com.template.mapapplication.di

import com.template.data.login.SharedPrefsAuthenticationImpl
import com.template.data.login.SharedPrefsDB
import com.template.data.login.SharedPrefsRegistrationImpl
import com.template.domain.login.Authentication
import com.template.domain.login.Registration
import org.koin.dsl.module

val dataModule = module {
    single<Authentication> {
        SharedPrefsAuthenticationImpl(context = get())
    }

    single<Registration> {
        SharedPrefsRegistrationImpl(context = get())
    }

    single<SharedPrefsDB> {
        SharedPrefsDB(context = get())
    }
}