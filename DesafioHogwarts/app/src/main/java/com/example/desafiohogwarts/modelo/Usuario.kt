package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(val id:Int, val nombre:String,val email:String, val contraseña:String, val foto:String,val id_casa:Int,val exp:Int,val nivel:Int,val estado:String)
