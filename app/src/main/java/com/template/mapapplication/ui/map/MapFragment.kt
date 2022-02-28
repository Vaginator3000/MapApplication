package com.template.mapapplication.ui.map

import android.util.Log
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapapplication.PermissionsManager
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

    private fun requestAllPermissions() {
        val permissionsManager = PermissionsManager(this)
        permissionsManager.requestGeoPermission()
        permissionsManager.requestLocationPermission()
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

    override fun onStop() {
        mapKitFactory.onStop()
        binding.mapview.onStop()
        super.onStop()
    }
}