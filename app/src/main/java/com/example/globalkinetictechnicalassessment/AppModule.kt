package com.example.globalkinetictechnicalassessment

import com.example.globalkinetictechnicalassessment.connectivity.IConnectionManager
import com.example.globalkinetictechnicalassessment.connectivity.VolleyConnectionManager
import com.example.globalkinetictechnicalassessment.location.GooglePlayServicesLocationManager
import com.example.globalkinetictechnicalassessment.location.ILocationManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindLocationManager(locationManager: GooglePlayServicesLocationManager): ILocationManager

    @Singleton
    @Binds
    abstract fun bindConnectionManager(connectionManager: VolleyConnectionManager): IConnectionManager

}