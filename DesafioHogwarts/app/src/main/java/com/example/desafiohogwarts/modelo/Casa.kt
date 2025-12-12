package com.example.desafiohogwarts.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Casa(@SerializedName("id_casa")
              val id_casa:Int?=null,
                @SerializedName("nombre")
              val nombre:String? = null,

                @SerializedName("puntuacion")
              val puntuacion:Int? = null,
                @SerializedName("num_alumnos")
              val num_alumnos:Int? = null): Serializable
