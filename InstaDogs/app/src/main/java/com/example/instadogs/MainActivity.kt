package com.example.instadogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instadogs.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//14 habilitar onclickLisener para el buscador
class MainActivity : AppCompatActivity(),SearchView.OnQueryTextListener {
// 1 agregar viewBinding
    private lateinit var binding:ActivityMainBinding
    //7 crear variable adapter
    private lateinit var adapter: DogAdapter
    //8 crear lista mutable de las imagenes que se obtienen de la url
    private val imagenDogs = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //15 implementar 14
        binding.BuscarDogs.setOnQueryTextListener(this)

    //4 llamar metodos de recyclerView
        iniRecyclerView()

    }
    //5  crear metodos de recyclerView
    private fun iniRecyclerView(){
        //9 pasar el listado de imagenes al adapter
        adapter = DogAdapter(imagenDogs)
        //6 llamar el recicler view creado.
    binding.recyclerViewDogs.layoutManager = LinearLayoutManager(this)
        //10
        binding.recyclerViewDogs.adapter = adapter
    }

    //2 crear metodos retrofit
    private fun getRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //3 crear funcion de Corutinas
    private fun buscarXNombre(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(DogAPIServices::class.java).getDogsXRaza("$query/images")
            val cachorros = call.body()
            //11 todo lo q ejecutemos en este objeto se abrira en hilo principal de la app
            runOnUiThread{
                if (call.isSuccessful){
                    //13 anadir listado de imagenes al adapter
                    val images = cachorros?.fotoDog ?: emptyList()
                    imagenDogs.clear()
                    imagenDogs.addAll(images)
                    adapter.notifyDataSetChanged()

                }else{
                    // 12 show error
                    showError()
                }
            }

        }
    }

    // 12
    fun showError(){
        Toast.makeText(this,"Ha ocurrido un error, compruebe los su conexión a internet y reintente ",Toast.LENGTH_SHORT).show()

    }

    //14
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            buscarXNombre(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}