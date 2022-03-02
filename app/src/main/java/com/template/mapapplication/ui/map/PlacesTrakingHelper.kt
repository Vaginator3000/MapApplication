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

        if (currentLocation == null || previousLocation == null) return

        CoroutineScope(Dispatchers.IO).launch {
            val geocode = "${currentLocation?.position?.longitude}, ${currentLocation?.position?.latitude}"
            Log.e("AA", "geocode - $geocode")
            val address = GeocodeRepositoryImpl().getAddress(key = KeyClass().GeocodeKey, geocode = geocode)

            // первый элемент из этого списка - полный адрес. Его буду добавлять в базу
            address.response.GeoObjectCollection.featureMember.forEach {
                val index = address.response.GeoObjectCollection.featureMember.indexOf(it)
                Log.e("AA", "$index - ${it.GeoObject.metaDataProperty.GeocoderMetaData.text}")
            }
        }
    }

    override fun onLocationStatusUpdated(p0: LocationStatus) = Unit

    fun startTrackingLocation() {
        locationManager.subscribeForLocationUpdates(
            0.0, /*desiredAccuracy, 0 for best possible accuracy*/
            10000, /*minTime between location updates in milliseconds */
            0.0, /*minDistance between location updates in meters */
            false, /*allowUseInBackground */
            FilteringMode.OFF, /*filteringMode */
            this /*locationListener */
        )
    }

    fun stopTrackingLocation() {
        locationManager.unsubscribe(this)
    }
}