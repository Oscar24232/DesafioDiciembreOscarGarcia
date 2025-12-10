package com.example.desafiohogwarts.modelo

@kotlinx.serialization.Serializable
data class Hechizo(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val experiencia_alumno: Int
)