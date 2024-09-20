package com.example.instadogs


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


// crea el metodo por el cual entra nuestro servicio a consumir esa API
interface DogAPIServices {
    @GET
    suspend fun getDogsXRaza(@Url url:String): Response<DogRespuesta>

}