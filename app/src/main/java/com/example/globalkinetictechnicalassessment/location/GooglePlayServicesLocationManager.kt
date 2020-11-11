package com.example.globalkinetictechnicalassessment.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.globalkinetictechnicalassessment.R
import com.google.android.gms.location.*
import javax.inject.Inject

class GooglePlayServicesLocationManager @Inject constructor() : ILocationManager {

    private val locationPermissionRequestCode = 101
    private val debugTag = "LocationManager"

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locatedCallback: (Location) -> Unit

    private var currentLocation: Location? = null
        set(value) {
            field = value
            value?.let { locatedCallback(it) }
        }


    override fun startLocationUpdates(fragment: Fragment, callback: (Location) -> Unit) {
        handleLocationServicePermissions(fragment)
        locatedCallback = callback
    }

    override fun handleOnRequestPermissionsResult(
        context: Context?,
        requestCode: Int,
        grantResults: IntArray
    ) {

        if (requestCode == locationPermissionRequestCode) {
            if (grantResults.isNotEmpty()
                && grantResults.first() == PackageManager.PERMISSION_GRANTED
            ) {
                locateUser(context)
            } else {
                showRequestLocationPermissionRationale(context)
            }
        }
    }

    override fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun handleLocationServicePermissions(fragment: Fragment) {
        val context = fragment.context ?: return

        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                locateUser(context)
            }
            fragment.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                showRequestLocationPermissionRationale(context)
            }
            else -> {
                fragment.requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    locationPermissionRequestCode
                )
            }
        }
    }

    private fun showRequestLocationPermissionRationale(context: Context?) {
        Toast.makeText(context, R.string.permission_coarse_location_explained, Toast.LENGTH_LONG)
            .show()
        // TODO Improve
    }

    @SuppressLint("MissingPermission")
    private fun locateUser(context: Context?) {
        context ?: return

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let { currentLocation = it }
        }
        val locationRequest = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let { currentLocation = it.locations.last() }
                val delayInMillis: Long = 30000
                Handler(Looper.getMainLooper()).postDelayed({
                    Log.d(debugTag, "Stopped receiving location updates")
                    stopLocationUpdates()
                }, delayInMillis)
            }
        }
        Log.d(debugTag, "Started receiving location updates")
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}