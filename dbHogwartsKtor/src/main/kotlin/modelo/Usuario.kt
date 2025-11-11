package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(val dni:String, val nombre:String, val clave:String, val tfno:String, val rol: String)
