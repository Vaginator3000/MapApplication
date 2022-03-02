package com.template.mapapplication.ui.map

import android.graphics.Color
import android.graphics.PointF
import android.util.Log
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapapplication.R
import com.template.mapapplication.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(R.layout.fragment_map), UserLocationObjectListener {
    private val binding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)

    private val mapViewModel by viewModel<MapViewModel>()

    private val mapKitFactory by lazy { MapKitFactory.getInstance() }
    private val userLocationLayer by lazy { mapKitFactory.createUserLocationLayer(binding.mapview.mapWindow) }
    private val locationManager by lazy { mapKitFactory.createLocationManager() }

    override fun onStart() {
        super.onStart()
        initMap()
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
    }

    private fun setMapOnCurrentLocation() {
        userLocationLayer.isVisible = true
        //userLocationLayer.isHeadingEnabled = true
        userLocationLayer.setObjectListener(this)
        PlacesTrakingHelper(locationManager).startTrackingLocation()
    }

    override fun onStop() {
        PlacesTrakingHelper(locationManager).stopTrackingLocation()
        mapKitFactory.onStop()
        binding.mapview.onStop()
        super.onStop()
    }

    override fun onObjectAdded(userLocationView : UserLocationView) {
        with(binding) {
            userLocationLayer.setAnchor(
                PointF((mapview.width * 0.5).toFloat(), (mapview.height * 0.5).toFloat()),
                PointF((mapview.width * 0.5).toFloat(), (mapview.height * 0.83).toFloat() )
            )
        }

        userLocationView.accuracyCircle.fillColor = Color.BLUE and -0x66000001
    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
    }
}