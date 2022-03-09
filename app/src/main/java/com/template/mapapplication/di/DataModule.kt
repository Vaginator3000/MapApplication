package com.template.mapapplication.di

import com.template.data.login.SharedPrefsAuthenticationImpl
import com.template.data.db.SharedPrefsLoginDB
import com.template.data.db.SharedPrefsVisitedPlacesDB
import com.template.data.login.SharedPrefsRegistrationImpl
import com.template.data.repository.SharedPrefsVisitedPlacesRepositoryImpl
import com.template.domain.login.Authentication
import com.template.domain.login.Registration
import com.template.domain.repository.VisitedPlacesRepository
import org.koin.dsl.module

val dataModule = module {
    single<Authentication> {
        SharedPrefsAuthenticationImpl(sharedPrefsDB = get())
    }

    single<Registration> {
        SharedPrefsRegistrationImpl(sharedPrefsDB = get())
    }

    single<VisitedPlacesRepository> {
        SharedPrefsVisitedPlacesRepositoryImpl(context = get())
    }

    single<SharedPrefsLoginDB> {
        SharedPrefsLoginDB(context = get())
    }

    single<SharedPrefsVisitedPlacesDB> {
        SharedPrefsVisitedPlacesDB(context = get())
    }
}