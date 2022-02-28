package com.template.mapapplication

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
import com.fondesa.kpermissions.allDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.google.android.gms.location.LocationSettingsRequest

class PermissionsManager(private val fragment: Fragment) {

    private fun checkLocationPermissionIsGranted() : Boolean {
        return ActivityCompat.checkSelfPermission(
            fragment.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            fragment.requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkIsGeoEnabled(): Boolean {
        val locationManager =
            fragment.requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun requestLocationPermission() {
        if (checkLocationPermissionIsGranted()) return
        LocationSettingsRequest.Builder().setAlwaysShow(true)
        fragment.permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
            .build().send { result ->
                if (result.allDenied())
                    showToast(fragment.getString(R.string.location_permission_is_denied))
            }

    }

    fun requestGeoPermission() {
        if (checkIsGeoEnabled()) return
        AlertDialog.Builder(fragment.requireContext())
            .setMessage(fragment.getString(R.string.geo_is_disabled))
            .setPositiveButton(fragment.getString(R.string.open_settings)) { _,_ ->
                fragment.startActivity( Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(fragment.getString(R.string.cansel)) { _, _ -> {} }
            .create()
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(fragment.requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}