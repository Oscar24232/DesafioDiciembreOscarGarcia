package com.example.desafiohogwarts.modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class CasaAsignada(
    @SerialName("id_casa")
    val idCasa: Int
)