package com.template.mapapplication.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapapplication.R
import com.template.mapapplication.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapFragment : Fragment(R.layout.fragment_map) {
    private val binding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)

    private val mapViewModel by lazy { ViewModelProvider(this).get(MapViewModel::class.java) }

    private val mapKitFactory by lazy { MapKitFactory.getInstance() }
    private val userLocationLayer by lazy { mapKitFactory.createUserLocationLayer(binding.mapview.mapWindow) }
    private val locationManager by lazy { mapKitFactory.createLocationManager() }

    override fun onStart() {
        super.onStart()
        initMap()
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

    override fun onStop() {
        mapKitFactory.onStop()
        binding.mapview.onStop()
        super.onStop()
    }
}