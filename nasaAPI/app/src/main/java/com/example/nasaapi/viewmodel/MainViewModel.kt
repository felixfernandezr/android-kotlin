package com.example.nasaapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapi.RetrofitClient
import com.example.nasaapi.model.Apod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun fetchData(apiKey: String, onResult: (Apod?) -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            val response = RetrofitClient.api.getApod(apiKey, date = "2003-05-04")
            if (response.isSuccessful){
                onResult(response.body())
            } else {
                onResult(null)
            }
        }
    }
}
