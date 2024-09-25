package com.example.weatherapp.api

import com.example.weatherapp.api.model.Current
import com.example.weatherapp.api.model.Location

data class WeatherModel(
    val current: Current,
    val location: Location
)