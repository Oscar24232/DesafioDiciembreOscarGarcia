package com.example.desafiohogwarts.modelo

import kotlinx.serialization.Serializable

@Serializable
data class PreferenciasCasa(
    val preferencias: Map<String, Int>
)
