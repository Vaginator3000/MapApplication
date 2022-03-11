package com.template.mapapplication.ui.map

import com.template.data.geocode.GeocodeRepositoryImpl
import com.template.domain.repository.VisitedPlacesRepository
import com.template.mapapplication.KeyClass
import com.template.mapapplication.R
import com.template.models.GeoResponseModel
import com.template.models.VisitedPlaceModel
import com.yandex.mapkit.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.text.SimpleDateFormat
import java.util.*

class PlacesTrakingHelper(private val locationManager: LocationManager) : LocationListener, KoinComponent {
    private var currentLocation : Location? = null
    private val spVisitedPlacesRepositoryImpl by inject<VisitedPlacesRepository>()

    override fun onLocationUpdated(p0: Location) {
        currentLocation = p0
    }

    fun saveCurrentLocation() {
        currentLocation ?: return
        CoroutineScope(Dispatchers.IO).launch {
            val geocode = "${currentLocation!!.position.longitude}, ${currentLocation!!.position.latitude}"
            val address = getAddressByGeocode(geocode)
            addPlaceToDB(address)
        }
    }

    fun saveLocationByGeocode(geocode : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val address = getAddressByGeocode(geocode)
            addPlaceToDB(address)
        }
    }

    suspend fun getAddressByGeocode(geocode : String) : String {
        return GeocodeRepositoryImpl()
            .getAddress(key = KeyClass().GeocodeKey, geocode = geocode)
            .getFullAddress()
    }

    private fun addPlaceToDB(address: String) {
        val currentDate = SimpleDateFormat("dd/M/yyyy").format(Date())
        val currentTime = SimpleDateFormat("hh:mm").format(Date())
        val place = VisitedPlaceModel(address = address,
                                        date = currentDate,
                                        time = currentTime)
        CoroutineScope(Dispatchers.IO).launch {
            spVisitedPlacesRepositoryImpl.addOrUpdatePlace(place)
        }
    }

    private fun GeoResponseModel.getFullAddress() : String {
        return this.response.GeoObjectCollection.featureMember.first().GeoObject.metaDataProperty.GeocoderMetaData.text
    }

    override fun onLocationStatusUpdated(p0: LocationStatus) = Unit

    fun startTrackingLocation() {
        locationManager.subscribeForLocationUpdates(
            0.0, /*desiredAccuracy, 0 for best possible accuracy*/
            (R.integer.time_between_location_updates_in_milliseconds).toLong(), /*minTime between location updates in milliseconds */
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