package com.example.clase3_23_08

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clase3_23_08.adapter.DogAdapter
import com.example.clase3_23_08.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.svDogs.setOnQueryTextListener(this)

        initRecycler()
    }

    private fun initRecycler(){
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
    }

    private fun getRetrofit():Retrofit{
        //  DATO. Patrón de arquitectura Builder, objeto flexible
        return Retrofit.Builder()
            //  URL base de la API:
            .baseUrl("https://dog.ceo/api/breed/")
            //  Transformar el formato JSON:
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query:String){
        //  Primera utilización de CORRUTINAS. Rutinas paralelas para no estar trabajando siempre en el mismo hilo de procesamiento

        //  En otro hilo (IO) hago la llamada
        CoroutineScope(Dispatchers.IO).launch{
            val call = getRetrofit().create(ApiService::class.java).getDogsByBreed("$query/images") //  Flexibilidad para mostrar la raza
            val puppies = call.body()

            //  Una vez que yo tengo la respuesta, necesito mostrarla en la pantalla.
            runOnUiThread{ //  Lo que se ejecuta dentro este bloque de código sucede en el hilo principal
                if(call.isSuccessful){
                    //  Si trajo info, guardo en images; si no, genera una lista vacía (para que no rompa la app)
                    val images = puppies?.images ?: emptyList()
                    //  Reemplazo dogImages por cada búsqueda
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError(){
        Toast.makeText(this, "Acá hubo un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //  Siempre que la query no sea nula
        query?.let {
            searchByName(it)
            return true
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}