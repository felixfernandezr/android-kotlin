package com.example.nasaapi

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.nasaapi.databinding.ActivityMainBinding
import com.example.nasaapi.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val apiKey = BuildConfig.NASA_API_KEY

        viewModel.fetchData(apiKey){ apod ->    //  Función lambda
            apod?.let { //  El let hace que trabajemos sobre datos que NO SON NULOS
                lifecycleScope.launch {
                    Log.d("apod", "${it.date} || ${it.explanation}")
                    showPicture(it.url)
                    binding.tvExplanation.text = it.explanation
                }
            }

        }
    }

    fun showPicture(url:String){
        Picasso.with(this).load(url).into(binding.ivApod)
    }
}