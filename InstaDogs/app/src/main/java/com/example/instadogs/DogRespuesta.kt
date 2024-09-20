package com.example.instadogs

import com.google.gson.annotations.SerializedName


// Serialize me permite convertir el la variable definida por la api y traducirla a otra variable que voy a usar en el codigo
data class DogRespuesta(@SerializedName("message") var fotoDog: List<String> ,
                        @SerializedName("status") var status: String
                        )
