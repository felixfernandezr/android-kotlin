package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.api.WeatherModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.viewmodel.MainViewModel
import com.example.weatherapp.viewmodel.WeatherHandler

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var uiHandler: WeatherHandler

    private lateinit var loadingTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var countryTextView: TextView
    private lateinit var tempTextView: TextView
    private lateinit var conditionTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var windTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observer
        uiHandler = WeatherHandler(this)
        viewModel.weatherResult.observe(this) { uiState ->
            Log.d("MainActivity", "Weather state changed: $uiState")
            uiHandler.handleState(uiState)
        }

        // Binding TextViews
        loadingTextView = binding.loadingTextView
        cityTextView = binding.cityTextView
        countryTextView = binding.countryTextView
        tempTextView = binding.tempTextView
        conditionTextView = binding.conditionTextView
        humidityTextView = binding.humidityTextView
        windTextView = binding.windTextView

        // SearchView listener
        binding.searchEditText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    Log.i("query", "Search query: $query")
                    viewModel.fetchData(query)
                    hideKeyboard()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    // Handler actions
    fun showLoadingMessage() {
        binding.loadingTextView.text = "Loading..."
        loadingTextView.visibility = View.VISIBLE
    }

    fun updateUIWithWeatherData(data: WeatherModel) {
        binding.cityTextView.text = data.location.name + ", "
        binding.countryTextView.text = data.location.country
        binding.tempTextView.text = data.current.temp_c + "°C"
        binding.conditionTextView.text = data.current.condition.text
        binding.humidityTextView.text = "Humidity: " + data.current.humidity + "%"
        binding.windTextView.text = "Wind: " + data.current.wind_kph + " km/h"

        loadingTextView.visibility = View.GONE
    }

    fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        loadingTextView.visibility = View.GONE
    }

    // Hide keyboard
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this) // Use current focused view or create a new one
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

