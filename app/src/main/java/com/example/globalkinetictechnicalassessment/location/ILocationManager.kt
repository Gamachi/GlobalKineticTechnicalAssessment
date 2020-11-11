package com.example.globalkinetictechnicalassessment.location

import android.content.Context
import android.location.Location
import androidx.fragment.app.Fragment

interface ILocationManager {
    fun handleOnRequestPermissionsResult(context: Context?, requestCode: Int, grantResults: IntArray)
    fun startLocationUpdates(fragment: Fragment, callback: (Location) -> Unit)
    fun stopLocationUpdates()
}