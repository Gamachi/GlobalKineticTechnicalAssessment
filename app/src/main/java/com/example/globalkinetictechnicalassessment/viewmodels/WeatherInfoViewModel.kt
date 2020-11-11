package com.example.globalkinetictechnicalassessment.viewmodels

import android.content.Context
import android.location.Location
import android.util.Log
import com.example.globalkinetictechnicalassessment.R
import com.example.globalkinetictechnicalassessment.connectivity.IConnectionManager
import com.example.globalkinetictechnicalassessment.connectivity.VolleyGsonGetRequest
import com.example.globalkinetictechnicalassessment.location.ILocationManager
import com.example.globalkinetictechnicalassessment.models.WeatherInformation
import com.example.globalkinetictechnicalassessment.presentation.IWeatherInformation
import javax.inject.Inject

class WeatherInfoViewModel @Inject constructor() {

    private val debugTag = "WeatherInfoViewModel"

    private val context get() = view.getFragment().context

    lateinit var view: IWeatherInformation

    @Inject
    lateinit var locationManager: ILocationManager

    @Inject
    lateinit var connectionManager: IConnectionManager

    fun onCreateView() {
        refreshScreen()
    }

    fun onStop() {
        locationManager.stopLocationUpdates()
        connectionManager.cancelAllPendingRequests()
    }

    fun handleOnRequestPermissionsResult(
        context: Context?,
        requestCode: Int,
        grantResults: IntArray
    ) {
        locationManager.handleOnRequestPermissionsResult(context, requestCode, grantResults)
    }

    fun refreshButtonTapped() {
        refreshScreen()
    }

    private fun refreshScreen() {
        view.showProgressOverlay()
        locationManager.startLocationUpdates(view.getFragment()) {
            checkWeather(it)
        }
    }

    private fun checkWeather(location: Location) {
        Log.d(
            debugTag,
            "Retrieving weather for (lat:${location.latitude}, lon:${location.longitude})"
        )

        val apiKey = context?.resources?.getString(R.string.api_key) ?: return
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=${location.latitude}&lon=${location.longitude}&units=metric&appid=$apiKey"

        val gsonRequest =
            VolleyGsonGetRequest(url,
                WeatherInformation::class.java,
                null,
                { response ->
                    view.hideProgressOverlay()
                    view.displayWeatherInformation(response)
                },
                { error ->
                    connectionManager.handleError(context, error) {
                        checkWeather(location)
                    }
                }
            )

        connectionManager.enqueueRequest(gsonRequest)
    }
}