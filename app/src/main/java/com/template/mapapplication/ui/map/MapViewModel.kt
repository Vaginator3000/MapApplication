package com.template.mapapplication.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.template.models.LoginUserModel
import com.template.models.Result

class MapViewModel() : ViewModel() {
    lateinit var placesTrackingHelper : PlacesTrackingHelper
    var currentAddress : String = ""

    fun getAddressByGeocode(geocode: String) = liveData<Result<LoginUserModel>> {
        emit(Result.Loading())
        currentAddress = placesTrackingHelper.getAddressByGeocode(geocode)
        emit(Result.Success())
    }

    fun startTrackingLocation(placesTrackingHelper: PlacesTrackingHelper) {
        this.placesTrackingHelper = placesTrackingHelper
        this.placesTrackingHelper.startTrackingLocation()
    }

    fun saveLocationByGeocode(geocode: String) {
        placesTrackingHelper.saveLocationByGeocode(geocode)
    }

    fun saveCurrentLocation() {
        placesTrackingHelper.saveCurrentLocation()
    }
}