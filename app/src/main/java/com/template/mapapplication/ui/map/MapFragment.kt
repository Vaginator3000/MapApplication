package com.template.mapapplication.ui.map

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
import com.yandex.mapkit.map.CameraPosition
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(R.layout.fragment_map) {
    private val binding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)

    private val mapViewModel by viewModel<MapViewModel>()

    private val mapKitFactory by lazy { MapKitFactory.getInstance() }
    private val userLocationLayer by lazy { mapKitFactory.createUserLocationLayer(binding.mapview.mapWindow) }
    private val locationManager by lazy { mapKitFactory.createLocationManager() }

    override fun onStart() {
        super.onStart()
        initMap()
        requestAllPermissions()
        mapKitFactory.onStart()
        binding.mapview.onStart()
    }

    private fun initMap() {
        MapKitFactory.initialize(requireContext())
        binding.mapview.map.isRotateGesturesEnabled = false
        binding.mapview.map.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )
    }

    private fun requestAllPermissions() {
        requestGeoPermission()
        requestLocationPermission()
    }

    private fun checkLocationPermissionIsGranted() : Boolean {
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
            Manifest.permission.ACCESS_COARSE_LOCATION)
            .build().send { result ->
                if (result.allDenied())
                    showToast(getString(R.string.location_permission_is_denied))
            }
    }

    fun requestGeoPermission() {
        if (checkIsGeoEnabled()) return
        AlertDialog.Builder(context)
            .setMessage(getString(R.string.geo_is_disabled))
            .setPositiveButton(getString(R.string.open_settings)) { _,_ ->
                startActivity( Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(getString(R.string.cansel)) { _, _ -> {} }
            .create()
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        mapKitFactory.onStop()
        binding.mapview.onStop()
        super.onStop()
    }
}