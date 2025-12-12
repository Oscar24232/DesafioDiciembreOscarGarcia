package com.example.desafiohogwarts.modelo

import com.google.gson.annotations.SerializedName

data class Alumno_asignatura(@SerializedName("id_usuario")
                val id_usuario:Int?=null,
                @SerializedName("id_asignatura")
                val id_asignatura:String? = null)