package com.example.desafiohogwarts.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Casa(@SerializedName("nombre")
              val nombre:String? = null,

           @SerializedName("puntuacion")
              val puntuacion:String? = null): Serializable {

}