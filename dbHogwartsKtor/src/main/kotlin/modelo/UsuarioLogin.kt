package com.example.modelo

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioLogin(val dni:String, val clave:String)
