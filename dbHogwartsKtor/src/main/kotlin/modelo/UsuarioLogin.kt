package com.example.modelo

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioLogin(val email:String, val contrasena:String)
