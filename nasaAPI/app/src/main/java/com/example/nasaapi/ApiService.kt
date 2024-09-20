package com.example.nasaapi

import com.example.nasaapi.model.Apod
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("planetary/apod")
    suspend fun getApod(
        @Query("api_key") apiKey: String,
        @Query("date") date:String? = null,
        @Query("start_date") startDate:String? = null,
        @Query("end_date") endDate:String? = null,
        @Query("count") count:Int? = null,
        @Query("thumbs") thumbs:Boolean? = null,
    ): Response<Apod>
}