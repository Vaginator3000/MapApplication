package com.template.mapapplication.ui.map

import android.util.Log
import com.template.data.geocode.GeocodeRepositoryImpl
import com.template.mapapplication.KeyClass
import com.template.mapapplication.R
import com.template.models.GeoResponseModel
import com.yandex.mapkit.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PlacesTrakingHelper(private val locationManager: LocationManager) : LocationListener {
    var currentAddress : String? = null
    var previousAddress : String? = null

    override fun onLocationUpdated(p0: Location) {
        checkLocationIsChanged(p0)
    }

    private fun checkLocationIsChanged(newLocation: Location) {

        CoroutineScope(Dispatchers.IO).launch {
            val geocode = "${newLocation.position.longitude}, ${newLocation.position.latitude}"
            val address = GeocodeRepositoryImpl()
                            .getAddress(key = KeyClass().GeocodeKey, geocode = geocode)
                            .getFullAddress()

            previousAddress = currentAddress
            currentAddress = address
            if (previousAddress == null || currentAddress == null) this.cancel(null)

            //Если местоположение изменилось, значит юзер ушел в другое место и его фиксировать не надо
            if (previousAddress != currentAddress) this.cancel(null)

            TODO("save address to db")
        }
    }

    private fun GeoResponseModel.getFullAddress() : String {
        return this.response.GeoObjectCollection.featureMember.first().GeoObject.metaDataProperty.GeocoderMetaData.text
    }

    override fun onLocationStatusUpdated(p0: LocationStatus) = Unit

    fun startTrackingLocation() {
        locationManager.subscribeForLocationUpdates(
            0.0, /*desiredAccuracy, 0 for best possible accuracy*/
            (R.integer.time_between_location_updates_in_mills).toLong(), /*minTime between location updates in milliseconds */
            (R.integer.distance_between_location_updates_in_meters).toDouble(), /*minDistance between location updates in meters */
            false, /*allowUseInBackground */
            FilteringMode.OFF, /*filteringMode */
            this /*locationListener */
        )
    }

    fun stopTrackingLocation() {
        locationManager.unsubscribe(this)
    }
}