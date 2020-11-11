package com.example.globalkinetictechnicalassessment.models

data class WeatherInformation (
    var coord: Coord? = Coord(),
    var weather: List<Weather>? = listOf(),
    var base: String? = "",
    var main: Main? = Main(),
    var visibility: Int? = 0,
    var wind: Wind? = Wind(),
    var clouds: Clouds? = Clouds(),
    var dt: Int? = 0,
    var sys: Sys? = Sys(),
    var timezone: Int? = 0,
    var id: Int? = 0,
    var name: String? = "",
    var cod: Int? = 0
)