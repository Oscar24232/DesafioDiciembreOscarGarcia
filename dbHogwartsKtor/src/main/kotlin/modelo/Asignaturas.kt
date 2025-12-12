package com.example.desafiohogwarts.modelo

import kotlinx.serialization.Serializable


@Serializable
data class Asignaturas(
    val id_asignatura: Int,
    val nombre: String
)