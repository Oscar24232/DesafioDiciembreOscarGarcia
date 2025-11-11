package com.example.modelo

import kotlinx.serialization.Serializable

@Serializable
data class Persona(var dni: String, var nombre: String, var clave: String, var tfno: String,var rol: String)