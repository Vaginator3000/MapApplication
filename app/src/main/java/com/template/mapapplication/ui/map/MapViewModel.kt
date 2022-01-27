package com.template.mapapplication.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    val text = MutableLiveData("This is Map Fragment")
}