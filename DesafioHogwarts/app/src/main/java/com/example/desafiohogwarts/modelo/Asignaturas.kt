package com.example.desafiohogwarts.modelo

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class Asignaturas(
    val id_asignatura: Int,
    val nombre: String
)
