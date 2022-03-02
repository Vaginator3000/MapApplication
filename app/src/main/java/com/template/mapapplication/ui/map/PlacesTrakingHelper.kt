package com.template.mapapplication.ui.map

import android.util.Log
import com.template.data.geocode.GeocodeRepositoryImpl
import com.template.mapapplication.KeyClass
import com.yandex.mapkit.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesTrakingHelper(private val locationManager: LocationManager) : LocationListener {
    var currentLocation : Location? = null
    var previousLocation : Location? = null

    override fun onLocationUpdated(p0: Location) {
        checkLocationIsChanged(p0)
    }

    private fun checkLocationIsChanged(newLocation: Location) {
        previousLocation = currentLocation
        currentLocation = newLocation
        previousLocation ?: return

        CoroutineScope(Dispatchers.IO).launch {
            val geocode = "${currentLocation!!.position.longitude}, ${currentLocation!!.position.latitude}"
            val address = GeocodeRepositoryImpl().getAddress(KeyClass().GeocodeKey, geocode)

            // первый элемент из этого списка - полный адрес. Его буду добавлять в базу
           /* address.response.GeoObjectCollection.featureMember.forEach {
                val index = address.response.GeoObjectCollection.featureMember.indexOf(it)
                Log.e("AA", "$index - ${it.GeoObject.metaDataProperty.GeocoderMetaData.text}")
            }*/
        }
    }

    override fun onLocationStatusUpdated(p0: LocationStatus) {
        Log.e("AA", "status - " + p0.toString())
    }

    fun startTrackingLocation() {
        locationManager.subscribeForLocationUpdates(
            0.0,
            5000,
            0.0,
            true,
            FilteringMode.OFF,
            this
        )
    }

    fun stopTrackingLocation() {
        locationManager.unsubscribe(this)
    }
}