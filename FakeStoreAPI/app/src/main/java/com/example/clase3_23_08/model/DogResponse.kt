package com.example.clase3_23_08.model

import com.google.gson.annotations.SerializedName

//  Usamos Data Class cuando trabajamos con JSON

data class DogResponse(
    @SerializedName("message") //   La propiedad va a tomar el valor del campo con este nombre
    val images:List<String>,

    @SerializedName("status")
    val status:String

    //  Lo que viene en el campo "message" se usará como "images", lo que viene en el campo "status" se usará como "status", en nuestro modelo
)
