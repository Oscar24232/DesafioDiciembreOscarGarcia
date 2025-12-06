package com.example.modelo

import kotlinx.serialization.Serializable

@Serializable
class Casa(var id_casa:Int,var nombre:String,var puntuacion:Int, var num_alumnos: Int) {
}