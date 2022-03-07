package com.template.mapapplication.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.domain.repository.VisitedPlacesRepository

class MapViewModel(private val spVisitedPlacesRepositoryImpl : VisitedPlacesRepository) : ViewModel() {

    val text = MutableLiveData("This is Map Fragment")
}