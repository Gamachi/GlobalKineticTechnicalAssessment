package com.example.globalkinetictechnicalassessment.presentation

import androidx.fragment.app.Fragment
import com.example.globalkinetictechnicalassessment.models.WeatherInformation

/*
Fragment must override onRequestPermissionResult(...) and delegate to handleOnRequestPermissionsResult(...) of the VM
 */
interface IWeatherInformation {
    fun getFragment(): Fragment
    fun showProgressOverlay()
    fun hideProgressOverlay()
    fun displayWeatherInformation(weatherInformation: WeatherInformation)
}