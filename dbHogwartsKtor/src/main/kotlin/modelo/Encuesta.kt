package com.example.modelo

import kotlinx.serialization.Serializable

@Serializable
data class Encuesta(
    val id: Int? = null,
    val so: String? = null,
    val horas: Int? = null,
    val ciclo: String? = null,
    val modulos: String?,
    val dni_usuario: String
)
