package com.template.mapapplication.ui.places

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.domain.repository.VisitedPlacesRepository

class PlacesViewModel(private val spVisitedPlacesRepositoryImpl : VisitedPlacesRepository) : ViewModel() {

    val text = MutableLiveData("This is Places Fragment")
}