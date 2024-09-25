package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.RetrofitClient
import com.example.weatherapp.api.WeatherModel
import com.example.weatherapp.api.model.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val weatherApi = RetrofitClient.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun fetchData(city: String){
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = weatherApi.getWeather(Constants.apiKey, city)
                if (response.isSuccessful) {
                    Log.i("Response : ", response.body()?.toString() ?: "No Data")
                    response.body()?.let {
                        _weatherResult.postValue(NetworkResponse.Success(it))
                    }
                } else {
                    Log.e("Error : ", "Response Code: ${response.code()} - ${response.message()}")
                    _weatherResult.postValue(NetworkResponse.Error("Failed loading data"))
                }
            } catch (e: Exception) {
                Log.e("Exception : ", e.message ?: "Unknown error")
                _weatherResult.postValue(NetworkResponse.Error("Failed loading data"))
            }
        }
    }
}
