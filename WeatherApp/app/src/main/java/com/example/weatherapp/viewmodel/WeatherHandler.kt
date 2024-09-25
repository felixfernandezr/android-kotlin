package com.example.weatherapp.viewmodel

import com.example.weatherapp.MainActivity
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.WeatherModel

class WeatherHandler(private val activity: MainActivity) {

    fun handleState(response: NetworkResponse<WeatherModel>) {
        when (response) {
            is NetworkResponse.Loading -> {
                activity.showLoadingMessage()
            }
            is NetworkResponse.Success -> {
                activity.updateUIWithWeatherData(response.data)
            }
            is NetworkResponse.Error -> {
                activity.showError(response.message)
            }
            null -> {}
        }
    }
}