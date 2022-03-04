package com.template.mapapplication.di

import com.template.mapapplication.ui.login.LoginViewModel
import com.template.mapapplication.ui.map.MapViewModel
import com.template.mapapplication.ui.places.PlacesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<LoginViewModel> {
        LoginViewModel(
            sharedPrefsAuthImpl = get(),
            sharedPrefsRegImpl = get())
    }

    viewModel<MapViewModel> {
        MapViewModel(
            spVisitedPlacesRepositoryImpl = get()
        )
    }

    viewModel<PlacesViewModel> {
        PlacesViewModel(
            spVisitedPlacesRepositoryImpl = get()
        )
    }
}