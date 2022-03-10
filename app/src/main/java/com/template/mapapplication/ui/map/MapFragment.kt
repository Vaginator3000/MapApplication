package com.template.mapapplication.ui.map

import android.graphics.PointF
import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fondesa.kpermissions.allDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.google.android.gms.location.LocationSettingsRequest
import com.template.mapapplication.R
import com.template.mapapplication.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import kotlinx.coroutines.runBlocking


class MapFragment : Fragment(R.layout.fragment_map), UserLocationObjectListener, InputListener {
    private val binding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)

    private val mapViewModel by viewModel<MapViewModel>()

    private val mapKitFactory by lazy { MapKitFactory.getInstance() }
    private val userLocationLayer by lazy { mapKitFactory.createUserLocationLayer(binding.mapview.mapWindow) }
    private val locationManager by lazy { mapKitFactory.createLocationManager() }
    private val placesTrakingHelper by lazy { PlacesTrakingHelper(locationManager) }

    private var isLocationFound = false

    override fun onStart() {
        super.onStart()
        initMap()
        requestAllPermissions()
        mapKitFactory.onStart()
        binding.mapview.onStart()
        setMapOnCurrentLocation()
    }

    private fun initMap() {
        MapKitFactory.initialize(requireContext())
        binding.mapview.map.isRotateGesturesEnabled = false
        //захардкодил Москву как начальную позицию карты
        binding.mapview.map.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )
        binding.mapview.map.addInputListener(this)
    }

    private fun setMapOnCurrentLocation() {
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = false /* true - маркер статичный, вращается карта, false - наоборот */
        userLocationLayer.setObjectListener(this)
        placesTrakingHelper.startTrackingLocation()
    }

    private fun requestAllPermissions() {
        requestGeoPermission()
        requestLocationPermission()
    }

    private fun checkLocationPermissionIsGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkIsGeoEnabled(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun requestLocationPermission() {
        if (checkLocationPermissionIsGranted()) return
        LocationSettingsRequest.Builder().setAlwaysShow(true)
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).build().send { result ->
            if (result.allDenied())
                showToast(getString(R.string.location_permission_is_denied))
        }
    }

    fun requestGeoPermission() {
        if (checkIsGeoEnabled()) return
        AlertDialog.Builder(context)
            .setMessage(getString(R.string.geo_is_disabled))
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(getString(R.string.cansel)) { _, _ -> {} }
            .create()
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        PlacesTrakingHelper(locationManager).stopTrackingLocation()
        mapKitFactory.onStop()
        binding.mapview.onStop()
        super.onStop()
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        if (isLocationFound) return
        isLocationFound = true

        with(binding) {
            //Центрируем карту, установив точку с местоположением по центру
            val widthCenter = mapview.width / 2f
            val heightCenter = mapview.height / 2f
            userLocationLayer.setAnchor(
                PointF(widthCenter, heightCenter),
                PointF(widthCenter, heightCenter)
            )
        }

        userLocationView.accuracyCircle.fillColor = R.color.accuracy_circle_color
    }

    override fun onObjectRemoved(p0: UserLocationView) = Unit

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) = Unit

    override fun onMapTap(p0: Map, p1: Point) = Unit

    override fun onMapLongTap(p0: Map, p1: Point) {
        showSavePlaceRequestDialog(p1)
    }

    private fun showSavePlaceRequestDialog(point : Point) {
        runBlocking {
            val geocode = "${point.longitude}, ${point.latitude}"
            val address = placesTrakingHelper.getAddressByGeocode(geocode)
            val saveLocationRequestMsg = getString(R.string.save_place_request).format(address)

            AlertDialog.Builder(context)
                .setMessage(saveLocationRequestMsg)
                .setPositiveButton(getString(R.string.positive_answer)) { _, _ ->
                    placesTrakingHelper.saveLocationByGeocode(geocode)
                    showToast(getString(R.string.saving_success))
                }
                .setNeutralButton(getString(R.string.save_current_location_answer)) { _, _ ->
                    placesTrakingHelper.saveCurrentLocation()
                    showToast(getString(R.string.saving_success))
                }
                .setNegativeButton(getString(R.string.negative_answer)) { _, _ -> {} }
                .create()
                .show()
        }
    }
}
